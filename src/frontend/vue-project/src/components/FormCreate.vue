<template>
  <div class="my-40">
    <h1 class="text-center text-4xl font-extrabold">Creating a form</h1>

    <form @submit.prevent="submitForm" class="space-y-4">
      <!-- Tên form -->
      <div
        class="p-6 bg-white rounded-lg shadow-md mx-80 border-t-pink-800 border-t-8 my-20"
      >
        <div class="my-5 mx-3">
          <label for="formTitle" class="block font-medium">Title:</label>
          <input
            type="text"
            id="formTitle"
            v-model="form.title"
            class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
            required
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
        <div class="my-5 mx-3">
          <label for="expirationDate" class="block font-medium">Expiration Date:</label>
          <input
            type="datetime-local"
            id="expirationDate"
            v-model="form.expirationDate"
            class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-pink-700"
          />
        </div>
        <div>
          <!-- question -->
          <Question :questions="form.questions"></Question>
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
          Create form
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import Question from "./Question.vue";
import FormService from "@/services/FormService";
import { v4 as uuidv4 } from "uuid";

export default {
  //add component
  components: { Question },
  props: ["projectId"],
  data() {
    return {
      form: {
        title: "",
        description: "",
        projectId: "",
        expirationDate: "",  // <-- Thêm trường này
        questions: [
          {
            id: uuidv4(),
            content: "",
            formId: this.formId,
            type: "text",

            fileUrl: "", ////////////////////////////////////
            publicId: "",
            resourceType: "",
            textUrl: "",
            options: [],

            open: false,
          },
        ], //mac dinh mot cau hoi
      },
    };
  },

  methods: {
    async submitForm() {
      // Xử lý dữ liệu form ở đây
      this.form.expirationDate = new Date(this.form.expirationDate).toISOString(); // Chuyển về định dạng ISO
      const response = await FormService.createFormForProject(
        this.projectId,
        this.form
      );

      console.log("Form submitted:", this.form);
      alert("Form is created successfully!!");
      this.resetForm();
      this.$router.push({
        name: "project-details",
        params: { projectId: this.projectId },
      });
    },
    resetForm() {
      this.form = {
        title: "",
        description: "",
        projectId: "",
        quetions: [],
      };
    },

    addMoreQuestion() {
      this.form.questions.push({
        id: uuidv4(),
        content: "",
        formId: "",
        type: "text",
        options: [],

        publicId: "",
        resourceType: "",
        textUrl: "",
        open: false,
      });
      console.log("Added question:", this.form.questions);
    },
  },
};
</script>

<style scoped></style>
