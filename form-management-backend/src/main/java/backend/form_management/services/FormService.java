package backend.form_management.services;

import backend.form_management.models.*;
import backend.form_management.repositories.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FormService {
    @Autowired
    private FormRepository formRepository;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private CloudinaryService cloudinaryService;


    //get form by id
    public Optional<Form> getFormById(String formId) {
        return formRepository.findById(formId);
    }


    //create a form in the project
    public Form createForm(Form form) {
        Form newForm = formRepository.save(form);
        // set formId to each question
        for (int i = 0; i < newForm.getQuestions().size(); i++) {
            newForm.getQuestions().get(i).setFormId(newForm.getId());
        }
        // Nếu lastModifiedAt chưa có, gán bằng createdAt
        if (newForm.getLastModifiedAt() == null) {
            newForm.setLastModifiedAt(form.getCreatedAt());
        }
        return newForm;
    }


    //update form
    public Form updateForm(String formId, Form updatedForm) {
        Optional<Form> existingFormOpt = formRepository.findById(formId);
        if (existingFormOpt.isPresent()) {
            Form existingForm = existingFormOpt.get();

            // Lưu danh sách questionId hiện có trước khi cập nhật
            Set<String> oldQuestionIds = existingForm.getQuestions().stream()
                    .map(Question::getId)
                    .collect(Collectors.toSet());

            //id bên vue
//            //set questionId for new creation
//            for (int i = 0; i < updatedForm.getQuestions().size(); i++) {
//                Question question = updatedForm.getQuestions().get(i);
//                    if(question.getId()==null) {
//                        question.setId(UUID.randomUUID().toString());
//                    }
//            }
            existingForm.setTitle(updatedForm.getTitle());
            existingForm.setDescription(updatedForm.getDescription());
            existingForm.setQuestions(updatedForm.getQuestions());

            // Lưu danh sách questionId mới
            Set<String> newQuestionIds = updatedForm.getQuestions().stream()
                    .map(Question::getId)
                    .collect(Collectors.toSet());

            //old - new
            oldQuestionIds.removeAll(newQuestionIds);

            // Nếu có question bị xóa, cập nhật response để xóa answers liên quan
            if (!oldQuestionIds.isEmpty()) {
                responseService.removeAnswersByQuestionIds(formId, oldQuestionIds);
            }
            return formRepository.save(existingForm);
        } else {
            throw new RuntimeException("Form not found");
        }
    }

    //delete form
    public void deleteForm(String formId) throws IOException {
        Optional<Form> optionalForm = formRepository.findById(formId);
        if (optionalForm.isPresent()) {
            Form existingForm = optionalForm.get();

                //delete all image of form
                    //image of question
            List<Question> questions = existingForm.getQuestions();
            for (Question question : questions) {
                if(!question.getPublicId().isEmpty()) {
                    cloudinaryService.deleteFile(question.getPublicId(), question.getResourceType());
                }

                        //image of option
                List<Option> options = question.getOptions();
                for (Option option : options) {
                    if (!option.getPublicId().isEmpty() ) {
                        cloudinaryService.deleteFile(option.getPublicId(), option.getResourceType());
                    }
                }
            }

            //delete all responses of form
            List<Response> responses = responseService.getAllResponsesByFormId(formId);
            for (Response response : responses) {
                responseService.deleteResponse(response.getId());
            }
            //delete form
            formRepository.deleteById(formId);


        }
    }

    //delete form of project
    public List<Form> getAllFormsOfProject (String projectId){
        return formRepository.findAllByProjectId(projectId);
    }
}
