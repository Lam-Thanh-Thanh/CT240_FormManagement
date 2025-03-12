<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100">
    <div class="w-full max-w-md p-6 bg-white rounded-lg shadow-lg">
      <h2 class="text-2xl font-semibold text-center text-gray-700">Đăng Ký</h2>
      <form @submit.prevent="handleRegister" class="mt-4">
        <div>
          <label class="block text-sm font-medium text-gray-600">Tên đăng nhập</label>
          <input v-model="user.username" type="text" class="w-full px-4 py-2 mt-1 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400" required />
        </div>
        <div class="mt-4">
          <label class="block text-sm font-medium text-gray-600">Mật khẩu</label>
          <input v-model="user.password" type="password" class="w-full px-4 py-2 mt-1 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400" required />
        </div>
        <button type="submit" class="w-full mt-6 bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition">
          Đăng ký
        </button>
      </form>
      <p class="mt-4 text-center text-sm text-gray-600">
        Đã có tài khoản?
        <router-link to="/login" class="text-blue-500 hover:underline">Đăng nhập</router-link>
      </p>
    </div>
  </div>
</template>

<script>
import { register } from "@/services/auth";

export default {
  data() {
    return {
      user: { username: "", password: "" },
    };
  },
  methods: {
    async handleRegister() {
      try {
        await register(this.user);
        alert("Đăng ký thành công! Vui lòng đăng nhập.");
        this.$router.push("/login");
      } catch (error) {
        alert("Đăng ký thất bại!");
      }
    },
  },
};
</script>
