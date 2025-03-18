import axios from "axios";

const API_URL = "http://localhost:8080/api/users";


const apiClient = axios.create({
  baseURL: "http://localhost:8080/api/users",
  headers: {
    "Content-Type": "application/json",
  },
});

export default {
  getUserById(id) {
    return apiClient.get(`/${id}`);
  },
  getAllUsers() {
    return axios.get(API_URL);
  },
  updateUserRole(id, role) {
    return axios.put(`${API_URL}/${id}/role`, { role });
  },
  deleteUser(id) {
    return axios.delete(`${API_URL}/${id}`);
  },
};
