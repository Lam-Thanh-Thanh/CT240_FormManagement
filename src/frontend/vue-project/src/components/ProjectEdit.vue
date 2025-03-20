<template>
  <div class="my-40">
    <h1 class="text-center text-4xl font-extrabold">Project Edit</h1>

    <form @submit.prevent="createProject" class="">
      <div class="p-6 bg-white rounded-lg shadow-md my-20 mx-80">
        <!-- Tên Project -->
        <div class="mb-2">
          <label for="projectName" class="block font-bold">Name</label>
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
          <label for="projectDescription" class="block font-bold"
            >Description</label
          >
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
          v-on:click="updateProject()"
        >
          Save
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import ProjectService from "@/services/ProjectService";
import router from "@/router"; // Import router để điều hướng
import { jwtDecode } from "jwt-decode";
export default {
  props: ["projectId"],
  data() {
    return {
      project: { name: "", description: "" },
      userId: "",
    };
  },
  // mounted() {
  //   this.checkLogin(); // Kiểm tra đăng nhập khi component được mount
  // },
  async created() {
    const hasAccess = await this.checkLogin();
    if (hasAccess) {
      this.getProjectDetails();
    }
  },
  methods: {
    async checkLogin() {
      // Lấy token từ localStorage
      const token = localStorage.getItem("token");
      if (!token) {
        alert("You need to login first!");
        router.push("/login");
        return false;
      }

      try {
        // Giải mã token để lấy userId
        const decoded = jwtDecode(token);
        this.userId = decoded.sub; // Đảm bảo key trong token là 'sub' hoặc 'userId'

        // Lấy danh sách tất cả project của userId
        const response = await ProjectService.getAllProjectsOfUser(this.userId);

        if (response && response.data) {
          const userProjects = response.data;

          // Kiểm tra projectId có trong danh sách project của user không
          const isProjectValid = userProjects.some(
            (project) => project.id === this.projectId
          );

          if (!isProjectValid) {
            alert("You don't have permission to access this project!");
            router.push("/");
            return false;
          }
          return true;
        } else {
          alert("Cannot get the project list!");
          router.push("/");
          return false;
        }
      } catch (error) {
        console.error("Lỗi khi kiểm tra đăng nhập:", error);
        alert("Đã xảy ra lỗi, vui lòng thử lại!");
        router.push("/login");
        return false;
      }
    },

    formattedDate(createdAt) {
      return new Date(createdAt).toLocaleString("en-US", {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit",
        timeZoneName: "short",
      });
    },
    async getProjectDetails() {
      try {
        const response = await ProjectService.getProjectDetials(this.projectId);
        this.project = response.data;
        console.log(response.data);
      } catch (error) {
        console.error("There was an error getting project details:", error);
      }
    },
    async updateProject() {
      try {
        const response = await ProjectService.updateProject(
          this.projectId,
          this.project
        );
        console.log("Project updated:", this.project);
        alert("Project is updated successfully!!");
        this.$router.push({
          name: "project-details",
          params: { projectId: this.projectId },
        });
      } catch (error) {
        console.error("There was an error updating project:", error);
      }
    },
  },
};
</script>

<style scoped></style>
