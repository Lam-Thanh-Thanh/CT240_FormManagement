<template>
  <div class="py-20 px-60 border-b">
    <div class="flex gap-16 items-start justify-between">
      <h1 class="text-4xl font-extrabold">Project Details</h1>
      <!-- dropdown -->
      <div class="text-center">
        <button
          v-on:click="open = !open"
          class="bg-zinc-200 rounded-full px-2 py-1 m-2 hover:shadow-lg"
          title="More options"
        >
          <i class="fa-solid fa-ellipsis"></i>
        </button>
        <div class="shadow-lg rounded-md py-4 bg-white" v-if="open">
          <div class="hover:bg-yellow-300 px-10 py-1">
            <button class="" v-on:click="editProject">Edit</button>
          </div>
          <div class="hover:bg-red-500 px-10 py-1 hover:text-white">
            <button class="" v-on:click="deleteProject">Delete</button>
          </div>
        </div>
      </div>
    </div>
    <!-- project information -->
    <div class="text-left mt-9">
      <div class="pb-3">
        <span class="font-bold pr-2">Name:</span>
        <span class="">{{ project.name }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Description:</span>
        <span class="">{{ project.description }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Created at:</span>
        <span class="">{{ formattedDate(project.createdAt) }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Update at:</span>
        <span class="">{{ formattedDate(project.lastModifiedAt) }}</span>
      </div>
    </div>
  </div>

  <!-- form list -->
  <div class="flex flex-wrap gap-14 justify-start px-60 pb-48 pt-32 bg-white">
    <h2 class="text-3xl text-center font-extrabold w-[100%] mb-5">Form List</h2>
    <!-- search -->
    <div class="flex w-[100%] justify-center pb-12">
      <div class="w-1/3">
        <div class="relative w-[100%]">
          <input
            v-model="searchQuery"
            type="text"
            placeholder="Search forms..."
            class="w-[100%] pl-10 pr-4 py-4 rounded-full text-gray-700 focus:outline-1 focus:outline-gray-300 shadow-md focus:shadow-sm placeholder-gray-500 focus:placeholder-gray-400 transition duration-300 ease-in-out"
            title="Search"
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
    <div
      class="w-[22%] bg-white shadow-lg hover:shadow-md rounded-2xl transition duration-300 ease-in-out"
      v-if="searchQuery"
      v-for="(form, index) in filteredForms"
      :key="index"
    >
      <button v-on:click="viewFormDetails(form.id)" class="w-full text-left">
        <div
          class="font-bold text-lg bg-gray-200 border-2 border-myLightNavy rounded-t-2xl text-center py-2 px-4 overflow-hidden whitespace-nowrap overflow-ellipsis"
        >
          {{ form.title }}
        </div>
        <div class="py-3 px-4">
          <p class="overflow-hidden whitespace-nowrap overflow-ellipsis">
            {{ form.description }}
          </p>
        </div>
        <!-- delete form -->
      </button>

      <button
        type="button"
        v-on:click="deleteForm(index)"
        class="float-right pb-3 px-6"
      >
        <i
          class="fa-regular fa-trash-can hover:bg-gray-200 p-2 rounded-full text-gray-400 hover:text-gray-700"
        ></i>
      </button>
    </div>
    <div
      class="w-[20%] bg-white shadow-lg hover:shadow-md rounded-2xl transition duration-300 ease-in-out"
      v-if="!searchQuery"
      v-for="(form, index) in project.forms"
      :key="index"
    >
      <button
        v-on:click="viewFormDetails(form.id)"
        class="w-full text-left"
        title="Click to view form details"
      >
        <div
          class="font-bold text-lg bg-gray-200 border-2 border-myLightNavy rounded-t-2xl text-center py-2 px-4 overflow-hidden whitespace-nowrap overflow-ellipsis"
        >
          {{ form.title }}
        </div>
        <div class="py-3 px-4">
          <p class="overflow-hidden whitespace-nowrap overflow-ellipsis">
            {{ form.description }}
          </p>
          <p
            v-if="!form.description"
            class="overflow-hidden whitespace-nowrap overflow-ellipsis"
          >
            No description ...
          </p>
        </div>
        <!-- delete form -->
      </button>

      <button
        type="button"
        v-on:click="deleteForm(index)"
        class="float-right pb-3 px-6"
        title="Remove form"
      >
        <i
          class="fa-regular fa-trash-can hover:bg-gray-200 p-2 rounded-full text-gray-400 hover:text-gray-700 transition duration-300 ease-in-out"
        ></i>
      </button>
    </div>
    <!-- create new -->

    <button
      v-on:click="addForm"
      class="w-[20%] px-6 py-4 bg-white shadow-lg hover:shadow-md rounded-2xl transition duration-300 ease-in-out"
      title="Add more form"
    >
      <p class="text-xl mb-2">Add form</p>
      <i class="fa-solid fa-plus"></i>
    </button>
  </div>
</template>

<script>
import ProjectService from "@/services/ProjectService";
import FormService from "@/services/FormService";
import router from "@/router"; // Import router để điều hướng
import { jwtDecode } from "jwt-decode";
export default {
  props: ["projectId"],
  data() {
    return {
      open: false,
      project: "",
      forms: [],
      userId: "",
      isLoggedIn: "",
      searchQuery: "", // Thêm searchQuery
    };
  },

  async created() {
    const hasAccess = await this.checkLogin();
    if (hasAccess) {
      this.getProjectDetails();
    }
  },
  computed: {
    filteredForms() {
      return this.project.forms.filter((form) =>
        form.title.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
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
    viewFormDetails(seletedFormId) {
      try {
        this.$router.push({
          name: "form-edit",
          params: { formId: seletedFormId, projectId: this.projectId },
        });
      } catch (error) {}
    },
    addForm() {
      try {
        this.$router.push({
          name: "form-create",
          params: { projectId: this.projectId },
        });
      } catch (error) {}
    },
    async deleteForm(index) {
      const confirmDelete = window.confirm(
        "Are you sure you want to delete this form?"
      );
      if (confirmDelete) {
        try {
          const response = await FormService.deleteFormOfProject(
            this.projectId,
            this.project.forms[index].id
          );
          this.project.forms.splice(index, 1);
          alert("Form is deleted successfully!!");
          console.log(response.data);
        } catch (error) {
          console.error("Error deleting form:", error);
          alert("Failed to delete the form. Please try again.");
        }
      }
    },
    async deleteProject() {
      const confirmDelete = window.confirm(
        "Are you sure you want to delete this project?"
      );
      if (confirmDelete) {
        try {
          const response = await ProjectService.deleteProject(this.project.id);
          console.log(response.data);
          alert("Project is deleted successfully!!");

          // Chuyển hướng về danh sách dự án sau khi xóa thành công
          this.$router.push({ name: "project-list" });
        } catch (error) {
          console.error("Error deleting project:", error);
          alert("Failed to delete the project. Please try again.");
        }
      }
    },
    async editProject() {
      this.$router.push({
        name: "project-edit",
        params: { projectId: this.projectId },
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
