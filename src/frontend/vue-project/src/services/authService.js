import {jwtDecode} from "jwt-decode";

export const AuthService = {
  getToken() {
    return localStorage.getItem("token");
  },
  setToken(token) {
    localStorage.setItem("token", token);
  },
  removeToken() {
    localStorage.removeItem("token");
  },
  isAuthenticated() {
    return !!localStorage.getItem("token");
  },
  getUserRole() {
    const token = this.getToken();
    if (!token) return null;
    try {
      const decodedToken = jwtDecode(token);
      return decodedToken.role;
    } catch (error) {
      console.error("Lỗi giải mã token:", error);
      return null;
    }
  },
};