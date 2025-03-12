package backend.form_management.services;


import backend.form_management.models.Response;
import backend.form_management.repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.List;
import java.util.Set;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepository responseRepository;



    //create a response of the form
    public Response createResponse(String formId, Response response) {

        response.setFormId(formId);
        return responseRepository.save(response);
    }

    //get all response of form
    public List<Response> getAllResponseByFormId(String formId) {
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
    public void deleteResponse(String responseId) {
        responseRepository.deleteById(responseId);
    }
}
