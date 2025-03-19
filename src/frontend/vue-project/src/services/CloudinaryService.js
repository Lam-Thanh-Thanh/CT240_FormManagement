import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/projects",
  headers: {
    "Content-Type": "application/json",
    "Content-Type": "multipart/form-data",
  },
});

export default {
  uploadFile(file) {
    return apiClient.post(`/upload-file`, file);
  },

  deleteFile(publicId, resourceType) {
    return apiClient.delete("/delete-file", {
      params: { publicId, resourceType },
    });
  },
};
