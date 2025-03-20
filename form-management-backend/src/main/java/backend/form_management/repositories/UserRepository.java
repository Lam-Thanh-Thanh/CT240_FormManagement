package backend.form_management.repositories;

import backend.form_management.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username); // Nếu cần tìm theo username
    boolean existsByUsername(String username);  // Thêm dòng này
}
