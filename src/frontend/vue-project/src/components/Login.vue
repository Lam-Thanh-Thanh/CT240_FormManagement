<template>
    <div class="flex items-center justify-center min-h-screen bg-gray-100">
      <div class="w-full max-w-md p-6 bg-white rounded-lg shadow-lg">
        <h2 class="text-2xl font-semibold text-center text-gray-700">Đăng Nhập</h2>
        <form @submit.prevent="handleLogin" class="mt-4">
          <div>
            <label class="block text-sm font-medium text-gray-600">Tên đăng nhập</label>
            <input v-model="user.username" type="text" class="w-full px-4 py-2 mt-1 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400" required />
          </div>
          <div class="mt-4">
            <label class="block text-sm font-medium text-gray-600">Mật khẩu</label>
            <input v-model="user.password" type="password" class="w-full px-4 py-2 mt-1 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400" required />
          </div>
          <button type="submit" class="w-full mt-6 bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition">
            Đăng nhập
          </button>
        </form>
        <p class="mt-4 text-center text-sm text-gray-600">
          Chưa có tài khoản?
          <router-link to="/register" class="text-blue-500 hover:underline">Đăng ký</router-link>
        </p>
      </div>
    </div>
  </template>
  
  <script>
  import { login } from "@/services/auth";
  import { AuthService } from "@/services/authService";
  
  export default {
    data() {
      return {
        user: { username: "", password: "" },
      };
    },
    methods: {
      async handleLogin() {
        try {
          const response = await login(this.user);
          AuthService.setToken(response.data); // Lưu token
          localStorage.setItem("token", response.data.token);
          alert("Đăng nhập thành công!");
          this.$router.push("/");
        } catch (error) {
          alert("Đăng nhập thất bại!");
        }
      },
    },
  };
  </script>
  