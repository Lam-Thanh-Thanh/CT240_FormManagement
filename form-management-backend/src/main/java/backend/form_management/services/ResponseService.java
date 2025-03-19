package backend.form_management.services;


import backend.form_management.models.Answer;
import backend.form_management.models.Form;
import backend.form_management.models.Response;
import backend.form_management.repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private FileStorageService fileStorageService;



    //create a response of the form
    public Response createResponse(String formId, Response response) {
        Response newResponse = responseRepository.save(response);

        response.setFormId(formId);
        return newResponse;
    }

    //get all response of form
    public List<Response> getAllResponsesByFormId(String formId) {
        return responseRepository.getAllByFormId(formId);
    }



    //delete answer
    public void removeAnswersByQuestionIds(String formId, Set<String> questionIds) {
        List<Response> responses = responseRepository.getAllByFormId(formId);
             for (Response response : responses) {
                 response.getAnswers().removeIf(answer -> questionIds.contains(answer.getQuestionId()));
                 responseRepository.save(response);
             }

    }



    //delete response
    public void deleteResponse(String responseId) throws IOException {
        Optional<Response> optionalResponse = responseRepository.findById(responseId);
        responseRepository.deleteById(responseId);
        if (optionalResponse.isPresent()) {
            Response existingResponse = optionalResponse.get();
            List<Answer> answers = existingResponse.getAnswers();
            for (Answer answer : answers) {
                if (!answer.getPublicId().isEmpty()) {
                    cloudinaryService.deleteFile(answer.getPublicId(), answer.getResourceType());
                }
                if (answer.getTextUrl() != null && !answer.getTextUrl().isEmpty()) {
                    fileStorageService.deleteFile(answer.getTextUrl());

                }
            }

        }
    }
}
