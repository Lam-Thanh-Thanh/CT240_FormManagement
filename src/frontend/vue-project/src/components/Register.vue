<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100">
    <div class="w-full max-w-md p-6 bg-white rounded-lg shadow-lg">
      <h2 class="text-2xl font-bold text-center text-gray-700">
        CREATE AN ACCOUNT
      </h2>
      <form @submit.prevent="handleRegister" class="mt-4">
        <!-- Username -->
        <div>
          <label class="block text-sm font-medium text-gray-600">Username</label>
          <input
            v-model="user.username"
            type="text"
            class="w-full px-4 py-2 mt-1 border rounded-lg focus:outline-none focus:ring-1"
            :class="{'border-red-500': errors.username}"
            @blur="validateUsername"
          />
          <p v-if="errors.username" class="text-red-500 text-sm mt-1">{{ errors.username }}</p>
        </div>

        <!-- Password -->
        <div class="mt-4">
          <label class="block text-sm font-medium text-gray-600">Password</label>
          <input
            v-model="user.password"
            type="password"
            class="w-full px-4 py-2 mt-1 border rounded-lg focus:outline-none focus:ring-1"
            :class="{'border-red-500': errors.password}"
            @blur="validatePassword"
          />
          <p v-if="errors.password" class="text-red-500 text-sm mt-1">{{ errors.password }}</p>
        </div>

        <!-- Nút Đăng Ký -->
        <button
          type="submit"
          class="bg-pink-700 hover:bg-pink-800 text-white px-4 py-2 rounded transition duration-300 ease-in-out w-full my-6"
          :disabled="isSubmitting"
        >
          Register
        </button>
      </form>

      <p class="mt-4 text-center text-sm text-gray-600">
        Have already an account?
        <router-link to="/login" class="text-pink-700 hover:underline">Login here</router-link>
      </p>
    </div>
  </div>
</template>

<script>
import { register, checkUsernameExists } from "@/services/auth"; // API kiểm tra username tồn tại

export default {
  data() {
    return {
      user: { username: "", password: "" },
      errors: { username: "", password: "" },
      isSubmitting: false,
    };
  },
  methods: {
    async validateUsername() {
  if (this.user.username.length < 5) {
    this.errors.username = "Username must be at least 5 characters long.";
  } else {
    this.errors.username = "";
    try {
      const response = await checkUsernameExists(this.user.username);
      if (response.exists) {
        this.errors.username = "Username is already taken.";
      }
    } catch (error) {
      console.error("Error checking username:", error);
    }
  }
},
    validatePassword() {
      const password = this.user.password;
      const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
      if (!regex.test(password)) {
        this.errors.password =
          "Password must be at least 8 characters, including uppercase, lowercase, number, and special character.";
      } else {
        this.errors.password = "";
      }
    },
    async handleRegister() {
      this.validateUsername();
      this.validatePassword();

      if (this.errors.username || this.errors.password) return;

      this.isSubmitting = true;
      try {
        await register(this.user);
        alert("Registration successful! Please login.");
        this.$router.push("/login");
      } catch (error) {
        alert("Registration failed!");
      } finally {
        this.isSubmitting = false;
      }
    },
  },
};
</script>
