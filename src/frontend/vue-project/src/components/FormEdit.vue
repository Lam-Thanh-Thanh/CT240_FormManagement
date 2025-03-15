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
            <button class="" v-on:click="formExport">View Results</button>
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

export default {
  components: { Question },
  props: ["projectId", "formId"],
  data() {
    return {
      open: false,
      // oldTitle: "form1",
      form: {},
    };
  },
  async created() {
    await this.getFormDetails();
  },

  methods: {
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
        console.log(response.data);
      } catch (error) {
        console.error("There was an error getting form details:", error);
      }
    },
    async addMoreQuestion() {
      const newQuestion = {
        id: uuidv4(),
        content: "",
        formId: this.formId,
        type: "text",
        imageUrl: "",
        publicId: "",

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
    },

    async deleteForm() {
      const response = await FormService.deleteFormOfProject(
        this.projectId,
        this.formId
      );
      alert("Form is deleted successfully!!");
      console.log(response.data);
      this.$router.push({
        name: "project-details",
        params: { projectId: this.projectId },
      });
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
