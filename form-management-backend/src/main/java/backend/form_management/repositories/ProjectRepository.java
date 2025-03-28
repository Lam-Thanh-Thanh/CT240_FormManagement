package backend.form_management.repositories;

import backend.form_management.models.Project;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, ObjectId> {


    Optional<Project> findById(String projectId);

    void deleteById(String id);

    List<Project> findAllByUserId(String userId);
}
