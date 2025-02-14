# FormManagement

## 1. Giới thiệu về dự án

FormManagement là một hệ thống tạo khảo sát, bảng câu hỏi, đăng ký, và thu thập thông tin cho nhiều mục đích khác nhau như nghiên cứu, đánh giá, tổ chức sự kiện, hoặc thu thập phản hồi từ khách hàng. Hệ thống giúp thu thập dữ liệu từ người dùng một cách dễ dàng và trực quan.

**Đối tượng sử dụng:**

- Cá nhân muốn tạo khảo sát.
- Giảng viên, sinh viên trong môi trường giáo dục.
- Doanh nghiệp cần thu thập phản hồi khách hàng.

## 2. Công nghệ sử dụng

- **Backend:** Java, Spring Boot.
- **Frontend:** HTML, CSS, JavaScript.
- **Cơ sở dữ liệu:** MongoDB.
- **Thư viện & công cụ:** Maven, jjwt (0.12.6), Swagger (SpringDoc OpenAPI).

## 3. Chức năng chính

- Đăng nhập, đăng ký, quản lý người dùng.
- Tạo dự án, khảo sát với nhiều loại câu hỏi.
- Chia sẻ khảo sát qua link rút gọn, mã QR, email.
- Thống kê khảo sát.

## 4. Hướng dẫn cài đặt

### 4.1. Cài đặt Backend

#### Bước 1: Clone dự án

```bash
git clone https://github.com/Lam-Thanh-Thanh/CT240_FormManagement.git
cd FormManagement
```

#### Bước 2: Cấu hình MongoDB

Cập nhật file `application.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/formmanagement
```

#### Bước 3: Chạy Backend

```bash
mvn spring-boot:run
```

### 4.2. Cài đặt Frontend

- Chạy frontend với server static hoặc mở file HTML trong trình duyệt.

## 5. Cấu trúc thư mục

```
FormManagement/
├── backend/
│   ├── src/main/java/com/example/demo/
│   │   ├── controller/ (AuthController, ProjectController...)
│   │   ├── model/ (Project.java, Response.java...)
│   │   ├── repository/ (ProjectRepository.java...)
│   │   ├── security/ (JwtUtil.java, SecurityConfig.java)
│   │   ├── config/ (CorsConfig.java, SwaggerConfig.java)
├── frontend/
│   ├── index.html
│   ├── script.js
│   ├── styles.css
```

## 6. API Documentation

Dự án sử dụng Swagger để mô tả API.

- Truy cập Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- API Docs: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 7. Đóng góp & Phát triển

- Đóng góp bằng cách tạo issue hoặc pull request.
- Thảo luận trong nhóm để cải tiến tính năng mới.

