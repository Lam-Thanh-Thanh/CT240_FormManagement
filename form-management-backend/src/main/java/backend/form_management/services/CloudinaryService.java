package backend.form_management.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    public String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

        String originalUrl = uploadResult.get("url").toString();
        String resourceType = uploadResult.get("resource_type").toString(); // Lấy loại tài nguyên (image, video)

        // Nếu là video, tối ưu hóa URL
        if ("video".equals(resourceType)) {
            originalUrl = originalUrl.replace("/upload/", "/upload/f_auto:video,q_auto/");
        }  // Nếu là hình ảnh, tối ưu hóa URL (chỉ f_auto, q_auto)
        else if ("image".equals(resourceType)) {
            originalUrl = originalUrl.replace("/upload/", "/upload/f_auto,q_auto/");
        }
        System.out.println("originalUrl: " + originalUrl);

        return originalUrl; // Trả về URL gốc nếu là hình ảnh
    }



    public String deleteFile(String publicId, String resourceType) throws IOException {

        if (resourceType == null || resourceType.isEmpty()) {
            resourceType = "image"; // Mặc định là ảnh nếu không truyền
        }
        System.out.println("Deleting file: publicId=" + publicId + ", resourceType=" + resourceType);

        Map result = cloudinary.uploader().destroy(publicId,
                ObjectUtils.asMap("resource_type", resourceType));

        String deleteResult = (String) result.get("result");
        if ("ok".equals(deleteResult)) {
            return "Xóa thành công!";
        } else {
            throw new RuntimeException("Lỗi khi xóa: " + deleteResult);
        }
    }

}
