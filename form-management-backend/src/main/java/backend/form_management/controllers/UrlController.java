package backend.form_management.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
@CrossOrigin(origins = "http://localhost:5173") 
public class UrlController {

    @GetMapping("/qr")
    public ResponseEntity<String> generateQRCode(@RequestParam String link) {
        // Trả về đường link để frontend tự xử lý tạo QR code
        return ResponseEntity.ok(link);
    }
}
