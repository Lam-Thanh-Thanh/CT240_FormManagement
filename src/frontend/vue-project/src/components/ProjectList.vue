<template v-if="isLoggedIn">
  <h1 class="text-center p-32 text-4xl font-extrabold">Project Management</h1>
  <!-- search -->
  <div class="flex justify-center pb-32">
    <div class="w-1/3">
      <div class="relative w-[100%]">
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Search forms..."
          class="w-[100%] pl-10 pr-4 py-4 rounded-full text-gray-700 focus:outline-1 focus:outline-gray-300 shadow-md focus:shadow-sm placeholder-gray-500 focus:placeholder-gray-400 transition duration-300 ease-in-out"
        />
        <div
          class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none"
        >
          <i
            class="fa-solid fa-magnifying-glass text-gray-300 transition duration-300 ease-in-out"
          ></i>
        </div>
      </div>
    </div>
  </div>

  <div class="flex flex-wrap px-28 pb-40 gap-20 justify-center">
    <!-- search results -->
    <div
      v-if="searchQuery"
      v-for="(project, index) in filteredProjects"
      :key="index"
      class="w-[25%] bg-white shadow-lg hover:shadow-md rounded-2xl text-left transition duration-300 ease-in-out"
    >
      <button v-on:click="viewProjectDetails(project.id)" class="w-full">
        <div
          class="font-bold text-lg bg-pink-50 border-2 border-pink-700 rounded-t-2xl text-center py-2 px-4 overflow-hidden whitespace-nowrap overflow-ellipsis"
        >
          {{ project.name }}
        </div>
        <div class="py-5 px-4">
          <p class="line-clamp-2">
            {{ project.description }}
          </p>
        </div>
      </button>
      <!-- delete project-->
      <div class="text-right pb-3 px-6">
        <button v-on:click="deleteProject(index)">
          <i
            class="fa-regular fa-trash-can hover:bg-gray-200 p-2 rounded-full text-gray-400 hover:text-gray-700"
          ></i>
        </button>
      </div>
    </div>
    <!-- project list -->
    <div
      v-if="!searchQuery"
      v-for="(project, index) in projects"
      :key="index"
      class="w-[25%] bg-white shadow-lg hover:shadow-md rounded-2xl text-left transition duration-300 ease-in-out"
    >
      <button v-on:click="viewProjectDetails(project.id)" class="w-full">
        <div
          class="font-bold text-lg bg-pink-50 border-2 border-pink-700 rounded-t-2xl text-center py-2 px-4 overflow-hidden whitespace-nowrap overflow-ellipsis"
        >
          {{ project.name }}
        </div>
        <div class="py-5 px-4">
          <p class="line-clamp-2">
            {{ project.description }}
          </p>
        </div>
      </button>
      <!-- delete project-->
      <div class="text-right pb-3 px-6">
        <button v-on:click="deleteProject(index)">
          <i
            class="fa-regular fa-trash-can hover:bg-gray-200 p-2 rounded-full text-gray-400 hover:text-gray-700"
          ></i>
        </button>
      </div>
    </div>
    <!-- create new -->

    <button
      v-on:click="createProject"
      class="w-[25%] px-6 py-4 bg-white shadow-lg hover:shadow-md rounded-2xl transition duration-300 ease-in-out"
    >
      <p class="text-xl mb-2">Create new</p>
      <i class="fa-solid fa-plus"></i>
    </button>
  </div>
</template>

<script>
import ProjectService from "@/services/ProjectService";

import router from "@/router"; // Import router để điều hướng
import { jwtDecode } from "jwt-decode";
export default {
  data() {
    return {
      projects: [],
      isLoggedIn: false,
      userId: "",
      searchQuery: "", // Thêm searchQuery
    };
  },
  // mounted() {
  //   this.checkLogin(); // Kiểm tra đăng nhập khi component được mount
  // },
  async created() {
    await this.fetchAllProjects();
  },
  computed: {
    filteredProjects() {
      return this.projects.filter(
        (project) =>
          project.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          project.description
            .toLowerCase()
            .includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    async fetchAllProjects() {
      try {
        // Lấy token từ localStorage
        const token = localStorage.getItem("token");
        if (!token) {
          alert("You need to login first!");
          router.push("/login"); // Chuyển hướng đến trang đăng nhập
          return;
        }

        // Giải mã token để lấy userId
        const decoded = jwtDecode(token);
        this.userId = decoded.sub; // Đảm bảo key trong token là 'userId'
        console.log("User ID:", this.userId); // Kiểm tra userId
        const response = await ProjectService.getAllProjectsOfUser(this.userId);
        this.projects = response.data;
        console.log("API Response:", response); // Kiểm tra dữ liệu trả về
      } catch (error) {
        console.error("There was an error fetching the projects:", error);
      }
    },

    viewProjectDetails(seletedId) {
      try {
        this.$router.push({
          name: "project-details",
          params: { projectId: seletedId },
        });
      } catch (error) {}
    },
    createProject() {
      try {
        this.$router.push({
          name: "project-create",
        });
      } catch (error) {}
    },
    async deleteProject(index) {
      const confirmDelete = window.confirm(
        "Are you sure you want to delete this project?"
      );
      if (confirmDelete) {
        try {
          const response = await ProjectService.deleteProject(
            this.projects[index].id
          );
          this.projects.splice(index, 1);
          console.log(response.data);
          alert("Project deleted successfully!");
        } catch (error) {
          console.error("Error deleting project:", error);
          alert("Failed to delete the project. Please try again.");
        }
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
