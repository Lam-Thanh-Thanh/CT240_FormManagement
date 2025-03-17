import axios from "axios";

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
};
