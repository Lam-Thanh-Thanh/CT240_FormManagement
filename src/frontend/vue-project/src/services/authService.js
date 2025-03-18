import { jwtDecode } from "jwt-decode";

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
    try {
        const token = this.getToken();
        if (!token) return null;  

        const decoded = jwtDecode(token);
        console.log("Decoded Token:", decoded); // Kiểm tra dữ liệu

        // Kiểm tra nếu roles là một mảng và lấy giá trị đầu tiên
        if (decoded.roles && Array.isArray(decoded.roles)) {
            return decoded.roles[0]; // Lấy giá trị đầu tiên của mảng roles
        }

        return "USER"; // Mặc định nếu không tìm thấy role
    } catch (error) {
        console.error("Lỗi khi giải mã token:", error);
        return "USER";
    }
}
,
isAdmin() {
  const role = this.getUserRole();
  console.log("User Role:", role); // Debug xem role có đúng không
  return role === "ADMIN"; // So sánh trực tiếp
},
};
