<template>
  <div
    class="bg-white flex h-[30vh] lg:h-[65vh] justify-center items-center px-40 gap-60 border-b"
  >
    <div>
      <p class="text-3xl lg:text-7xl font-extrabold animate-bounce">Welcome!</p>
    </div>

    <div>
      <img
        src="../assets/undraw_customer-survey_2l89.svg"
        alt="welcome-img"
        class="w-[100%] h-[100%]"
      />
    </div>
  </div>

  <!-- Kiểm tra đăng nhập, nếu đã đăng nhập thì hiển thị ProjectList -->
  <div>
    <ProjectList />
  </div>
</template>

<script>
import Login from "@/components/Login.vue";
import ProjectList from "@/components/ProjectList.vue";
import { AuthService } from "@/services/authService";
import router from "@/router"; // Import router để điều hướng
export default {
  components: { ProjectList, Login },
  data() {
    return {
      // isLoggedIn: false, // Trạng thái đăng nhập
    };
  },
  // mounted() {
  //   this.checkLogin(); // Kiểm tra đăng nhập khi component được mount
  // },
  methods: {
    // checkLogin() {
    //   this.isLoggedIn = !!AuthService.getToken(); // Kiểm tra token, nếu có thì set isLoggedIn = true
    //   if (!this.isLoggedIn) {
    //     alert("Bạn cần đăng nhập trước!");
    //     router.push("/login"); // Chuyển hướng đến trang đăng nhập
    //   }
    // },
  },
};
</script>

<style lang="scss" scoped></style>
