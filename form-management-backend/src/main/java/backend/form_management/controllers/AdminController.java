package backend.form_management.controllers;

import backend.form_management.models.User;
import backend.form_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import java.util.Set;
import backend.form_management.security.JwtUtil; // Thêm import này


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    // Lấy danh sách tất cả người dùng
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        Set<String> roles = jwtUtil.extractRoles(jwt);
    
        if (!roles.contains("ADMIN")) {
            return ResponseEntity.status(403).body("Bạn không có quyền truy cập!");
        }
        return ResponseEntity.ok(userRepository.findAll());
    }

    // Lấy thông tin một user
    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id);
    }
    @PutMapping("/users/{id}/role")
public ResponseEntity<?> updateUserRole(@PathVariable String id, @RequestBody Map<String, Object> request) {
    Optional<User> userOptional = userRepository.findById(id);
    
    if (userOptional.isPresent()) {
        User user = userOptional.get();
        
        // 🟢 Chuyển đổi JSON từ Object sang Set<String>
        Object roleObject = request.get("roles");
        if (!(roleObject instanceof List<?> roleList)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid role format"));
        }

        Set<String> newRoles = new HashSet<>();
        for (Object r : roleList) {
            if (r instanceof String role) {
                newRoles.add(role);
            }
        }

        // 🟢 Cập nhật roles đúng kiểu
        user.setRoles(newRoles);
        userRepository.save(user);
        
        return ResponseEntity.ok(Map.of("message", "User role updated successfully"));
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
}

    // Xóa user (chỉ ADMIN mới có quyền)
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return "User deleted with id: " + id;
    }
}
