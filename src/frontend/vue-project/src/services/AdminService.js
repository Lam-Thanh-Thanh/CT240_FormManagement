import axios from "axios";
import { AuthService } from "./authService";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api/admin",
  headers: {
    "Content-Type": "application/json",
  },
});

export default {
    getAllUsers() {
    return apiClient.get("/users", {
        headers: { Authorization: `Bearer ${AuthService.getToken()}` },
    });
},

updateUserRole(userId, newRole) {
  return apiClient.put(`/users/${userId}/role`, { roles: [newRole] });
},

  deleteUser(id) {
    return apiClient.delete(`/users/${id}`, {
      headers: { Authorization: `Bearer ${AuthService.getToken()}` },
    });
  },
};
