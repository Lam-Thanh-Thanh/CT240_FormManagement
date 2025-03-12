package backend.form_management.repositories;

import backend.form_management.models.Form;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends MongoRepository<Form, String> {
    List<Form> findAllByProjectId(String projectId);
}
