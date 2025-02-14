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

## 4. Thành viên dự án

1. Lâm Thanh Thanh B2203529
2. Nguyễn Thị Tâm Minh B2203511
3. Dương Nguyễn Tường Vân B2203537
4. Đồng Thanh Tuyền B2012164

## 5. Hướng dẫn cài đặt

### 5.1. Cài đặt Backend

#### Bước 1: Clone dự án

```bash
git clone https://github.com/Lam-Thanh-Thanh/CT240_FormManagement.git
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

### 5.2. Cài đặt Frontend

- Chạy frontend với server static hoặc mở file HTML trong trình duyệt.

## 6. Cấu trúc thư mục

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
## Giải thích ý nghĩa cây thư mục
## 1. Backend
Thư mục backend/ chứa mã nguồn của phần máy chủ (server-side), viết bằng Java + Spring Boot.

### a. controller/ (Lớp điều khiển)
Chứa các file Java đảm nhận xử lý request từ client:
- AuthController.java - Xử lý xác thực (login, register).
- SurveyController.java - Quản lý khảo sát.
- ResponseController.java - Xử lý câu trả lời của người dùng.
### b. model/ (Lớp mô hình dữ liệu)
Chứa các class mô phỏng dữ liệu được lưu trong MongoDB.
- User.java - Định nghĩa thông tin người dùng.
- Survey.java - Định nghĩa thông tin khảo sát.
- Response.java - Định nghĩa câu trả lời từ người tham gia.
### c. repository/ (Lớp truy vấn dữ liệu)
Tương tác với MongoDB để lấy, lưu, cập nhật dữ liệu.
- UserRepository.java - Thao tác với collection User trong MongoDB.
- SurveyRepository.java - Truy vấn dữ liệu của khảo sát.
### d. security/ (Bảo mật & xác thực)
Chứa các class liên quan đến bảo mật hệ thống.
- JwtUtil.java - Tạo & xác thực JWT token.
- SecurityConfig.java - Cấu hình bảo mật API với Spring Security.
### e. config/ (Cấu hình hệ thống)
Chứa các file cấu hình quan trọng.
- CorsConfig.java - Cấu hình CORS cho phép frontend truy cập API.
- SwaggerConfig.java - Cấu hình Swagger UI để mô tả API.


## 2. Frontend
Thư mục frontend/ chứa giao diện của website, viết bằng HTML, CSS, JavaScript.

- index.html - Giao diện chính.
- script.js - Xử lý logic trên frontend (gửi request đến backend).
- styles.css - Định dạng giao diện.
## 7. API Documentation

Dự án sử dụng Swagger để mô tả API.

- Truy cập Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- API Docs: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)