<template>
  <header
    class="flex justify-between items-center h-[60px] px-10 border-solid border-b-gray-200 border-b-2"
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
          >Home</span
        ></router-link
      >
      <router-link
        to="/projects"
        class="text-xl font-normal px-7 hover:text-gray-900"
      >
        <span
          class="hover:border-b-2 hover:border-b-pink-700 transition duration-500 ease-in-out"
          >Projects</span
        ></router-link
      >
      <div>
        <router-link
          to="/user"
          class="text-xl font-bold pl-7 hover:text-gray-900"
          ><i class="fa-regular fa-user"></i
        ></router-link>
      </div>
      <button v-if="isAuthenticated" @click="handleLogout" class="bg-red-500 px-4 py-2 rounded hover:bg-red-600">
      Đăng xuất
      </button>
    </nav>
  </header>
</template>

<script>
import { AuthService } from "@/api/authService";

export default {
  computed: {
    isAuthenticated() {
      return AuthService.isAuthenticated();
    },
  },
  methods: {
    handleLogout() {
      AuthService.removeToken();
      this.$router.push("/");
    },
  },
};
</script>
<style lang="scss" scoped></style>
