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
  updateProject(projectId, projectData) {
    return apiClient.put(`/${projectId}/update`, projectData);
  },

  getAllProjectsOfUser(userId) {
    return apiClient.get(`/${userId}`);
  },
  getProjectDetials(projectId) {
    return apiClient.get(`/${projectId}/details`);
  },
  deleteProject(projectId) {
    return apiClient.delete(`/${projectId}`);
  },

  // getAllProjects() {
  //   return apiClient.get(`/`);
  // },
};
