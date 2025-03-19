<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100">
    <div class="w-full max-w-md p-6 bg-white rounded-lg shadow-lg">
      <h2 class="text-2xl font-bold text-center text-gray-700">
        CREATE AN ACCOUNT
      </h2>
      <form @submit.prevent="handleRegister" class="mt-4">
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
          Register
        </button>
      </form>
      <p class="mt-4 text-center text-sm text-gray-600">
        Have already an acccount?
        <router-link to="/login" class="text-pink-700 hover:underline"
          >Login here</router-link
        >
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
        alert("Registration successful! Please login.");
        this.$router.push("/login");
      } catch (error) {
        alert("Registration failed!");
      }
    },
  },
};
</script>
