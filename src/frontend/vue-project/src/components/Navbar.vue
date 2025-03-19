<template>
  <header
    class="flex justify-between items-center h-[70px] px-10 border-solid border-b-gray-200 border-b-2"
  >
    <div class="">
      <router-link to="/" class="text-3xl font-black"
        ><i class="fa-solid fa-leaf"></i>Form</router-link
      >
    </div>
    <nav class="flex justify-between items-center">
      <router-link to="/" class="text-xl font-normal px-7 hover:text-gray-900"
        ><span
          class="hover:border-b-2 hover:border-b-pink-700 transition duration-500 ease-in-out"
          title="Home"
          >Home</span
        ></router-link
      >
      <router-link
        to="/projects"
        class="text-xl font-normal px-7 hover:text-gray-900"
      >
        <span
          class="hover:border-b-2 hover:border-b-pink-700 transition duration-500 ease-in-out"
          title="Project Management"
          >Projects</span
        ></router-link
      >
      <router-link
        to="/admin"
        class="text-xl font-normal px-7 hover:text-gray-900"
        ><span
          class="hover:border-b-2 hover:border-b-pink-700 transition duration-500 ease-in-out"
          >Admin</span
        ></router-link
      >
      <!-- Biểu tượng User -->
      <div class="relative">
        <button
          @click="toggleDropdown"
          class="text-xl font-bold pl-3 hover:text-gray-900 focus:outline-none"
          title="Account"
        >
          <i class="fa-regular fa-user"></i>
        </button>

        <!-- Dropdown Menu -->
        <div
          v-if="isDropdownOpen"
          class="absolute right-0 mt-2 w-48 bg-white border rounded-lg shadow-lg py-2 z-50"
        >
          <router-link
            v-if="!isAuthenticated"
            to="/login"
            class="block px-4 py-2 text-gray-700 hover:bg-gray-100"
          >
            Login
          </router-link>
          <router-link
            v-if="!isAuthenticated"
            to="/register"
            class="block px-4 py-2 text-gray-700 hover:bg-gray-100"
          >
            Register
          </router-link>
          <router-link
            v-if="isAuthenticated"
            to="/account"
            class="block px-4 py-2 text-gray-700 hover:bg-gray-100"
          >
            Account
          </router-link>
          <button
            v-if="isAuthenticated"
            @click="handleLogout"
            class="block w-full text-left px-4 py-2 text-red-600 hover:bg-gray-100"
          >
            Log out
          </button>
        </div>
      </div>
    </nav>
  </header>
</template>

<script>
import { AuthService } from "@/services/authService";

export default {
  data() {
    return {
      isDropdownOpen: false,
    };
  },
  computed: {
    isAuthenticated() {
      return AuthService.isAuthenticated();
    },
  },
  methods: {
    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    handleLogout() {
      AuthService.removeToken();
      this.$router.push("/login");
      this.isDropdownOpen = false;
    },
  },
};
</script>

<style lang="scss" scoped></style>
