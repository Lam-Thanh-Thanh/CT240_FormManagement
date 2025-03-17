<template>
  <div class="my-40">
    <h1 class="text-center text-4xl font-extrabold">Project Creation</h1>

    <form @submit.prevent="createProject" class="">
      <div class="p-6 bg-white rounded-lg shadow-md my-20 mx-80">
        <!-- Tên Project -->
        <div class="mb-2">
          <label for="projectName" class="block">Name</label>
          <input
            type="text"
            id="projectName"
            v-model="project.name"
            class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
            required
          />
        </div>

        <!-- Mô tả Project -->
        <div class="mb-2">
          <label for="projectDescription" class="block">Description</label>
          <textarea
            id="projectDescription"
            v-model="project.description"
            class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
            rows="4"
            required
          ></textarea>
        </div>
      </div>
      <!-- form creation -->

      <!-- create project button -->
      <div class="text-center">
        <button
          type="submit"
          class="bg-pink-700 hover:bg-pink-800 text-white px-4 py-2 rounded transition duration-300 ease-in-out"
        >
          Create Project
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import { jwtDecode } from "jwt-decode";
import ProjectService from "@/services/ProjectService";
export default {
  data() {
    return {
      project: {
        name: "",
        description: "",

        forms: [],
        userId: "",
      },
    };
  },
  methods: {
    async createProject() {
      try {
        // Lấy token từ localStorage
        const token = localStorage.getItem("token");
        if (!token) {
          alert("Bạn chưa đăng nhập!");
          return;
        }

        // Giải mã token để lấy userId
        const decoded = jwtDecode(token);
        this.project.userId = decoded.sub; // Đảm bảo key trong token là 'userId'

        const response = await ProjectService.createProject(this.project);
        console.log("Project created:", response.data);
        alert("Project is created sucessfully !!");
        this.resetForm();
        this.$router.push({
          name: "project-details",
          params: { projectId: response.data.id },
        });
      } catch (error) {
        console.error("There was an error creating the project:", error);
        alert("Project creation failed !!");
      }
    },
    resetForm() {
      this.project = {
        name: "",
        description: "",
      };
    },
  },
};
</script>

<style scoped>
/* Bạn có thể thêm CSS tùy chỉnh ở đây nếu cần */
</style>
