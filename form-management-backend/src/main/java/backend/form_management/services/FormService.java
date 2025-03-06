package backend.form_management.services;

import backend.form_management.models.Form;
import backend.form_management.models.Option;
import backend.form_management.models.Question;
import backend.form_management.models.Response;
import backend.form_management.repositories.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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




    //get form by id
    public Optional<Form> getFormById(String formId) {
        return formRepository.findById(formId);
    }



    //create a form in the project
    public Form createForm(Form form) {
        // Tìm form trong danh sách forms
        for (int i = 0; i < form.getQuestions().size(); i++) {
            form.getQuestions().get(i).setId(UUID.randomUUID().toString());
        }
        return formRepository.save(form);
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

            //set questionId for new creation
            for (int i = 0; i < updatedForm.getQuestions().size(); i++) {
                Question question = updatedForm.getQuestions().get(i);
                    if(question.getId()==null) {
                        question.setId(UUID.randomUUID().toString());
                    }
            }
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
    public void deleteForm(String formId) {
        formRepository.deleteById(formId);

        List<Response> responses = responseService.getAllResponseByFormId(formId);

        for (Response response : responses) {
          responseService.deleteResponse(response.getId());
        }
    }

    //delete form of project
    public List<Form> getAllFormsOfProject(String projectId) {
        return formRepository.findAllByProjectId(projectId);
    }




}
