<template>
  <div class="py-20 px-80 border-b">
    <div class="flex gap-16 items-start justify-between">
      <h1 class="text-4xl font-extrabold">{{ form.title }}</h1>
      <!-- dropdown -->
      <div class="text-right">
        <button
          v-on:click="open = !open"
          class="bg-zinc-200 rounded-full px-2 py-1 m-2 hover:shadow-lg"
        >
          <i class="fa-solid fa-ellipsis"></i>
        </button>
        <div class="shadow-lg rounded-md py-4 bg-white" v-if="open">
          <div class="bg-white hover:bg-yellow-300 px-7 py-2 border-b-gray-100">
            <button class="" v-on:click="formExport">Export</button>
          </div>
          <div class="bg-white hover:bg-yellow-300 px-7 py-2 border-b-gray-100">
            <button class="" v-on:click="viewResponses">Responses</button>
          </div>
          <div class="bg-white hover:bg-red-500 px-7 py-2 hover:text-white">
            <button class="" v-on:click="deleteForm">Delete</button>
          </div>
        </div>
      </div>
    </div>
    <!-- Form information -->
    <div class="text-left pt-10">
      <div class="pb-3">
        <span class="font-bold pr-2">Description:</span>
        <span class="">{{ form.description }}</span>
      </div>

      <div class="pb-3">
        <span class="font-bold pr-2">Created at:</span>
        <span class="">{{ formattedDate(form.createdAt) }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Updated at:</span>
        <span class="">{{ formattedDate(form.lastModifiedAt) }}</span>
      </div>
    </div>
  </div>

  <div class="p-40 bg-white">
    <form @submit.prevent="updateForm" colass="space-y-4">
      <!-- Tên form -->
      <div
        class="p-6 bg-white rounded-lg shadow-md mt-0 my-40 m-44 border-t-pink-800 border-t-8"
      >
        <div class="my-5 mx-3">
          <label for="formTitle" class="block font-medium">Title:</label>
          <input
            type="text"
            id="formTitle"
            v-model="form.title"
            class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
          />
        </div>

        <!-- Mô tả form -->
        <div class="my-5 mx-3">
          <label for="projectDescription" class="block font-medium"
            >Description:</label
          >
          <textarea
            id="projectDescription"
            v-model="form.description"
            class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
            rows="3"
          ></textarea>
        </div>
        <div>
          <!-- question -->
          <Question
            :questions="form.questions"
            v-model="form.questions"
          ></Question>
          <div class="text-center mt-10">
            <button
              v-on:click="addMoreQuestion"
              class="hover:bg-pink-800 hover:text-white text-pink-800 px-2 py-1 rounded-full border-pink-800 border transition duration-300 ease-in-out"
              type="button"
            >
              <i class="fa-solid fa-plus"></i>
            </button>
          </div>
        </div>
      </div>
      <!-- Nút Submit -->
      <div class="text-center">
        <button
          type="submit"
          class="bg-pink-700 hover:bg-pink-800 text-white px-4 py-2 rounded transition duration-300 ease-in-out"
        >
          Save
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import FormService from "@/services/FormService";
import Question from "./Question.vue";
import { v4 as uuidv4 } from "uuid";
import router from "@/router"; // Import router để điều hướng
import { jwtDecode } from "jwt-decode";
import ProjectService from "@/services/ProjectService";

export default {
  components: { Question },
  props: ["projectId", "formId"],
  data() {
    return {
      open: false,
      // oldTitle: "form1",
      form: {
        title: "",
        description: "",
        questions: [],
      },
      userId: "",
    };
  },
  async created() {
    const hasAccess = await this.checkLogin();

    if (hasAccess) {
      await this.getFormDetails();
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
        const response = await ProjectService.getAllProjects(this.userId);

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
    async getFormDetails() {
      try {
        const response = await FormService.getFormDetails(this.formId);
        this.form = response.data;
        if (this.form.projectId !== this.projectId) {
          alert("This form does not exist in the project!!");
          this.$router.push("/"); // Điều hướng về trang chính để tránh trang trắng
          return;
        }
        console.log(response.data);
      } catch (error) {
        console.error("There was an error getting form details:", error);
        alert("Không thể tải dữ liệu biểu mẫu. Vui lòng thử lại!");
        this.$router.push("/"); // Điều hướng về trang chính để tránh trang trắng
      }
    },
    async addMoreQuestion() {
      const newQuestion = {
        id: uuidv4(),
        content: "",
        formId: this.formId,
        type: "text",

        fileUrl: "", ////////////////////////////////////
        publicId: "",
        resourceType: "",
        options: [],

        open: false,
      };
      this.form.questions.push(newQuestion);
      const response = await FormService.updateForm(
        this.projectId,
        this.formId,
        this.form
      );
      console.log("Added question:", this.form.questions);
    },

    async updateForm() {
      const response = await FormService.updateForm(
        this.projectId,
        this.formId,
        this.form
      );

      console.log("Form updated:", this.form);
      alert("Form is updated successfully!!");
      // Reload trang sau khi cập nhật thành công
      window.location.reload();
    },

    async deleteForm() {
      const confirmDelete = window.confirm(
        "Are you sure you want to delete this form?"
      );
      if (confirmDelete) {
        try {
          const response = await FormService.deleteFormOfProject(
            this.projectId,
            this.formId
          );
          alert("Form is deleted successfully!!");
          console.log(response.data);

          // Chuyển hướng về trang chi tiết dự án sau khi xóa thành công
          this.$router.push({
            name: "project-details",
            params: { projectId: this.projectId },
          });
        } catch (error) {
          console.error("Error deleting form:", error);
          alert("Failed to delete the form. Please try again.");
        }
      }
    },
    formExport() {
      try {
        this.$router.push({
          name: "form-export",
          params: { formId: this.formId },
        });
      } catch (error) {}
    },
    viewResponses() {
      try {
        this.$router.push({
          name: "form-responses",
          params: { projectId: this.projectId, formId: this.formId },
        });
      } catch (error) {}
    },
  },
};
</script>

<style lang="scss" scoped></style>
