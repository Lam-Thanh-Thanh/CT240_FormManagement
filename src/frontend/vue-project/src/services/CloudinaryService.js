import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/projects",
  headers: {
    "Content-Type": "application/json",
    "Content-Type": "multipart/form-data",
  },
});

export default {
  uploadImage(imageFile) {
    return apiClient.post(`/upload-image`, imageFile);
  },
  deleteImage(publicId) {
    return apiClient.delete("/delete-image", {
      params: { publicId },
    });
  },
};
