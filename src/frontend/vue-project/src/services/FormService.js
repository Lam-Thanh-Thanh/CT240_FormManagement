import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/projects",
  headers: {
    "Content-Type": "application/json",
  },
});

export default {
  // getAllFormsOfProject(projectId) {
  //   return apiClient.get(`/${projectId}/details`);
  // },

  getFormDetails(formId) {
    return apiClient.get(`forms/${formId}`);
  },

  createFormForProject(projectId, formData) {
    return apiClient.post(`/${projectId}/form-create`, formData);
  },
  updateForm(projectId, formId, updatedformData) {
    return apiClient.put(
      `/${projectId}/forms/${formId}/update`,
      updatedformData
    );
  },

  deleteFormOfProject(projectId, formId) {
    return apiClient.delete(`/${projectId}/forms/${formId}/delete`);
  },

  createResponse(formId, responseData) {
    return apiClient.post(`forms/${formId}/response`, responseData);
  },
};
