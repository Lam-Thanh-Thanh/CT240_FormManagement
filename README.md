# FormManagement

## 1. Project Introduction

FormManagement is a system for creating surveys, questionnaires, registrations, and collecting information for various purposes such as research, evaluation, event organization, or customer feedback collection. The system allows users to collect data in an easy and intuitive way.

**Target Users:**

- Individuals who want to create surveys.
- Lecturers and students in educational environments.
- Businesses that need to collect customer feedback.

## 2. Technologies Used

- **Backend:** Java, Spring Boot.
- **Frontend:** Vue.js.
- **Database:** MongoDB.
- **Libraries & Tools:** Maven, jjwt (0.12.6)

## 3. Function Features

- User registration, login, and management.
- Create projects and surveys with multiple question types.
- Share surveys via link and QR codes.
- Survey statistics and analysis.

## 4. Project Members

1. Lâm Thanh Thanh B2203529
2. Nguyễn Thị Tâm Minh B2203511
3. Dương Nguyễn Tường Vân B2203537
4. Đồng Thanh Tuyền B2012164

## 5. Installation Guide

### 5.1. Backend Installation

#### Step 1: Clone the project

```bash
git clone https://github.com/Lam-Thanh-Thanh/CT240_FormManagement.git
```

#### Step 2: Install and configure MongoDB

Update the `application.properties` file:

```properties
spring.application.name=form-management

#database
spring.data.mongodb.database=form-management
server.port=8080
spring.data.mongodb.uri=mongodb://localhost:27017/form-management
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
upload.dir=uploads

```
#### Step 3: Install apache-maven-3.9.9-bin
#### Step 4: Install JDK 21
#### Step 5: Run Backend
Execute the following commands sequentially:
```bash
mvn clean install
mvn spring-boot:run
```

### 5.2. Frontend Installation

#### Step 1: Download and install NodeJS

#### Step 2: Install npm

```bash
npm install
```
#### Step 3: Run the following command:

```bash
npm run dev
```

#### Step 4: Access the website:

```bash
http://localhost:5173/
```

## 6. Directory Structure

```
FormManagement/
├── form-magement-backend/
│   ├── src/main/java/backend/form_management
│   │   ├── config/
│   │   ├── controllers/
│   │   ├── models/
│   │   ├── repositories/
│   │   ├── security/
│   │   ├── services/
│   │   ├── FormManagementApplication.java
├── src/frontend/vue-project/src
│   ├── assets/
│   ├── components/
│   ├── router/
│   ├── services/
│   ├── views/
│   ├── App.vue
│   ├── main.js
```