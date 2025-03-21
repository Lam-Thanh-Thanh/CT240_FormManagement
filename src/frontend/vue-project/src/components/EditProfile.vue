<template>
  <div
    class="flex items-center justify-center min-h-screen bg-gradient-to-r from-purple-400 via-pink-500 to-red-500"
  >
    <div class="bg-white shadow-lg rounded-lg p-6 w-96 text-center">
      <h2 class="text-2xl font-semibold text-gray-800 mb-4">Edit Profile</h2>

      <!-- Ảnh đại diện -->
      <div class="relative flex justify-center mb-4">
        <label for="avatarInput" class="cursor-pointer">
          <img
            :src="avatarPreview || user.avatar || defaultAvatar"
            alt="Avatar"
            class="w-24 h-24 rounded-full border-4 border-gray-300 shadow-md object-cover"
          />
        </label>
        <input
          type="file"
          id="avatarInput"
          @change="handleFileUpload"
          accept="image/*"
          class="hidden"
        />
      </div>

      <form @submit.prevent="updateProfile" class="space-y-4">
        <!-- Full Name -->
        <div>
          <label class="block text-gray-700 text-left">Full Name</label>
          <input
            v-model="user.fullName"
            type="text"
            class="w-full border px-3 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
          />
        </div>

        <!-- Username -->
        <div>
          <label class="block text-gray-700 text-left">Username</label>
          <input
            v-model="user.username"
            type="text"
            disabled
            class="w-full border px-3 py-2 rounded-lg bg-gray-100 cursor-not-allowed"
          />
        </div>

        <!-- Email -->
        <div>
          <label class="block text-gray-700 text-left">Email</label>
          <input
            v-model="user.email"
            type="email"
            class="w-full border px-3 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
          />
        </div>

        <!-- Nút "Change Password" -->
        <button
          type="button"
          @click="toggleChangePassword"
          class="w-full bg-gray-200 hover:bg-gray-300 text-gray-800 px-4 py-2 rounded-lg font-semibold shadow-md transition duration-300 ease-in-out"
        >
          🔑 {{ showPasswordField ? "Cancel" : "Change Password" }}
        </button>

        <!-- New Password (Chỉ hiển thị khi bấm "Change Password") -->
        <div v-if="showPasswordField">
          <label class="block text-gray-700 text-left">New Password</label>
          <input
            v-model="newPassword"
            type="password"
            class="w-full border px-3 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-pink-500"
          />
        </div>

        <!-- Nút Save -->
        <button
          type="submit"
          class="w-full bg-pink-600 hover:bg-pink-700 text-white px-4 py-2 rounded-lg font-semibold shadow-md transition duration-300 ease-in-out"
        >
          💾 Save Changes
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import UserService from "@/services/UserService";
import { jwtDecode } from "jwt-decode";
import router from "@/router";
import defaultAvatarImg from "@/assets/shiba-smug.gif";

export default {
  data() {
    return {
      user: {
        fullName: "",
        username: "",
        email: "",
        avatar: "",
      },
      avatarPreview: null,
      selectedFile: null,
      userId: "",
      defaultAvatar: defaultAvatarImg,
      showPasswordField: false, // Ẩn ô nhập password mặc định
      newPassword: "", // Chỉ dùng khi đổi mật khẩu
    };
  },
  async created() {
    await this.loadUserData();
  },
  methods: {
    async loadUserData() {
      const token = localStorage.getItem("token");
      if (!token) {
        alert("Bạn chưa đăng nhập!");
        router.push("/login");
        return;
      }
      const decoded = jwtDecode(token);
      this.userId = decoded.sub;

      try {
        const response = await UserService.getUserById(this.userId);
        this.user = { ...response.data };
        this.avatarPreview = this.user.avatar;
      } catch (error) {
        console.error("Lỗi khi lấy thông tin user:", error);
        alert("Không thể tải thông tin user.");
        router.push("/");
      }
    },
    handleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.avatarPreview = reader.result;
          this.user.avatar = reader.result;
        };
      }
    },
    toggleChangePassword() {
      this.showPasswordField = !this.showPasswordField;
      if (!this.showPasswordField) {
        this.newPassword = ""; // Reset password nếu hủy đổi
      }
    },
    async updateProfile() {
      try {
        const updatedUser = { ...this.user };

        // Xóa field password nếu người dùng không nhập mới
        if (this.showPasswordField) {
          if (this.newPassword.trim() !== "") {
            updatedUser.password = this.newPassword;
          } else {
            delete updatedUser.password;
          }
        } else {
          delete updatedUser.password;
        }

        await UserService.updateUser(this.userId, updatedUser);
        alert("Cập nhật thành công!");
        router.push("/account");
      } catch (error) {
        console.error("Lỗi khi cập nhật user:", error);
        alert("Cập nhật thất bại!");
      }
    },
  },
};
</script>
