<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100">
    <div class="w-full max-w-md p-6 bg-white rounded-lg shadow-lg">
      <h2 class="text-2xl font-bold text-center text-gray-700">LOGIN</h2>
      <form @submit.prevent="handleLogin" class="mt-4">
        <div>
          <label class="block text-sm font-medium text-gray-600"
            >Username</label
          >
          <input
            v-model="user.username"
            type="text"
            class="w-full px-4 py-2 mt-1 border rounded-lg focus:outline-none focus:ring-1 focus:ring-pink-700"
            required
          />
        </div>
        <div class="mt-4">
          <label class="block text-sm font-medium text-gray-600"
            >Password</label
          >
          <input
            v-model="user.password"
            type="password"
            class="w-full px-4 py-2 mt-1 border rounded-lg focus:outline-none focus:ring-1 focus:ring-pink-700"
            required
          />
        </div>
        <button
          type="submit"
          class="bg-pink-700 hover:bg-pink-800 text-white px-4 py-2 rounded transition duration-300 ease-in-out w-full my-6"
        >
          Login
        </button>
      </form>
      <p class="mt-4 text-center text-sm text-gray-600">
        Don't have an account?
        <router-link to="/register" class="text-pink-700 hover:underline"
          >Register here</router-link
        >
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
      AuthService.setToken(response.data.token); 
      const role = AuthService.getUserRole();
      
      alert("Log in successfully!");
      console.log("token", response.data.token);
      console.log(role);

      if (role === "ADMIN") {
        this.$router.push("/admin");
      } else {
        this.$router.push("/projects");
      }
    } catch (error) {
      alert("Login failed!");
    }
  },
},
};
</script>
