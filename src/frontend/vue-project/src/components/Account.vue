<template>
  <div class="flex items-center justify-center min-h-screen bg-gradient-to-r from-purple-400 via-pink-500 to-red-500">
    <div class="bg-white shadow-lg rounded-lg p-6 w-96">
      <div class="flex flex-col items-center m-6">
        <img :src="avatarPreview || user.avatar || defaultAvatar"
          class="w-24 h-24 rounded-full border-4 border-pink-700"
        />
        <h2 class="mt-4 text-xl font-semibold text-gray-700">
          {{ user.fullName }}
        </h2>
        <p class="text-gray-500">{{ user.email}}</p>
      </div>
      <div class="m-4">
        <button @click="goToEditProfile"
          class="bg-pink-700 hover:bg-pink-800 text-white px-4 py-2 rounded transition duration-300 ease-in-out w-full">
          Edit Profile
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import UserService from "@/services/UserService";
import router from "@/router"; // Import router để điều hướng
import { jwtDecode } from "jwt-decode";

export default {
  data() {
    return {
      user: {
        username: "",
      },
      userId: "",
      defaultAvatar: "https://www.w3schools.com/howto/img_avatar.png",
    };
  },
  async created() {
    await this.getUserInfo();
  },
  methods: {
    goToEditProfile() {
    this.$router.push(`/edit-profile/${this.userId}`);
    },
    async checkLogin() {
      // Lấy token từ localStorage
      const token = localStorage.getItem("token");
      if (!token) {
        alert("Bạn chưa đăng nhập!");
        router.push("/login");
        return false;
      }

      try {
        // Giải mã token để lấy userId
        const decoded = jwtDecode(token);
        this.userId = decoded.sub; // Đảm bảo key trong token là 'sub' hoặc 'userId'

        return true;
      } catch (error) {
        console.error("Lỗi khi kiểm tra đăng nhập:", error);
        alert("Đã xảy ra lỗi, vui lòng thử lại!");
        router.push("/login");
        return false;
      }
    },
    async getUserInfo() {
      // Đợi kết quả từ checkLogin()
      const isLoggedIn = await this.checkLogin();
      if (!isLoggedIn) return;

      try {
        const response = await UserService.getUserById(this.userId);
        this.user.fullName = response.data.fullName;
        console.log("fullName", response.data.fullName);
        this.user.email = response.data.email;
        console.log("email", response.data.email);
        this.user.avatar = response.data.avatar;
        console.log("avatar", response.data.avatar);
      } catch (error) {
        console.error("There was an error getting user infomation:", error);
        alert("Không thể tải thông tin user. Vui lòng thử lại!");
        this.$router.push("/"); // Điều hướng về trang chính để tránh trang trắng
      }
    },
  },
};
</script>

<style scoped></style>
