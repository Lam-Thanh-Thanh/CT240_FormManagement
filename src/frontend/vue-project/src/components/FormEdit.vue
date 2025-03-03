<template>
  <!-- dropdown -->
  <div class="flex gap-16 items-start mx-72 my-32 mb-20 justify-between">
    <h1 class="text-4xl font-extrabold">Form edit</h1>
    <div class="text-right">
      <button
        v-on:click="open = !open"
        class="bg-zinc-200 rounded-full px-2 py-1 m-2 hover:shadow-lg"
      >
        <i class="fa-solid fa-ellipsis"></i>
      </button>
      <div class="shadow-lg" v-if="open">
        <div class="hover:bg-yellow-300 px-2 py-1 border-b-gray-100 border-b-2">
          <button class="" v-on:click="formExport">View Results</button>
        </div>

        <div class="hover:bg-red-400 px-2 py-1">
          <button class="">Delete</button>
        </div>
      </div>
    </div>
  </div>

  <div class="mt-0 m-40">
    <!-- Form information -->
    <div class="text-left mx-32 my-20">
      <div class="pb-3">
        <span class="font-bold pr-2">Title:</span>
        <span class=""> {{ form.title }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Description:</span>
        <span class="">{{ form.description }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Project Name:</span>
        <span class=""> ..</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Create at:</span>
        <span class="">Date</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Update at:</span>
        <span class="">Date</span>
      </div>
    </div>

    <form @submit.prevent="updateForm" class="space-y-4">
      <!-- Tên form -->
      <div
        class="p-6 bg-white rounded-lg shadow-md mt-0 my-40 m-80 border-t-pink-800 border-t-8"
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
    async getFormDetails() {
      try {
        const response = await FormService.getFormDetails(this.formId);
        this.form = response.data;
        console.log(response.data);
      } catch (error) {
        console.error("There was an error getting form details:", error);
      }
    },
    addMoreQuestion() {
      this.form.questions.push({
        content: "",
        formId: "",
        type: "radio",
        options: [],
        // answer: [],
        TextOption: "",
        open: false,
        showChooseOption: true,
        showTextOption: false,
      });
      console.log("Added question:", this.form.questions);
    },

    async updateForm() {
      const response = await FormService.updateForm(
        this.projectId,
        this.formId,
        this.form
      );

      console.log("Form updated:", this.form);
      alert("Project đã được update thành công!");
    },

    formExport() {},
  },
};
</script>

<style lang="scss" scoped></style>
