import axios from "axios";

const API_URL = "http://localhost:8080/api/auth";

export const login = (user) => axios.post(`${API_URL}/login`, user);
export const register = (user) => axios.post(`${API_URL}/register`, user);

/**
 * Kiểm tra username đã tồn tại chưa
 * @param {string} username - Tên đăng nhập cần kiểm tra
 * @returns {Promise<boolean>} - Trả về true nếu username đã tồn tại, false nếu chưa
 */
export const checkUsernameExists = async (username) => {
    try {
      const response = await axios.get(`${API_URL}/check-username`, {
        params: { username },
      });
      return response.data; // Bây giờ sẽ trả về { exists: true/false }
    } catch (error) {
      console.error("Error checking username:", error.response?.status, error.message);
      return { exists: false }; // Tránh chặn đăng ký khi gặp lỗi
    }
  };
