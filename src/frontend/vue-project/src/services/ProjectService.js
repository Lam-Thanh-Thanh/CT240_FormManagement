import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/projects",
  headers: {
    "Content-Type": "application/json",
  },
});

export default {
  createProject(projectData) {
    return apiClient.post("/create", projectData);
  },
  getAllProjects() {
    return apiClient.get("");
  },
};
