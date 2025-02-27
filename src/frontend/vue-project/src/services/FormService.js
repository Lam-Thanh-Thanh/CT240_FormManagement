import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/projects",
  headers: {
    "Content-Type": "application/json",
  },
});

export default {
  getAllForms() {
    return apiClient.get("/forms");
  },
  createFormForProject(formData, projectId) {
    return apiClient.post(`/${projectId}/form-create`, formData);
  },
};
