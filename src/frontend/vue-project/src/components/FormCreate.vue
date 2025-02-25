<template>
  <div class="mb-60">
    <h1 class="text-center m-32 mb-20 text-4xl font-extrabold">
      Creating a form
    </h1>

    <form @submit.prevent="" class="space-y-4">
      <!-- Tên form -->
      <div
        class="p-6 bg-white rounded-lg shadow-md mt-0 my-40 m-80 border-t-pink-800 border-t-8"
      >
        <div class="my-5 mx-3">
          <label for="formTitle" class="block font-medium">Title:</label>
          <input
            type="text"
            id="formTitle"
            v-model="form.formTitle"
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
            v-model="form.formDescription"
            class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
            rows="3"
          ></textarea>
        </div>
        <!-- question -->
        <Question></Question>
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

export default {
  //add component
  components: { Question },

  data() {
    return {
      form: {
        formTitle: "",
        formDescription: "",
        questions: [],
      },
    };
  },
  methods: {
    submitForm() {
      // Xử lý dữ liệu form ở đây
      console.log("Form submitted:", this.form);
      alert("Project đã được thêm thành công!");
      this.resetForm();
    },
    resetForm() {
      this.form = {
        projectName: "",
        projectDescription: "",
        quetions: [],
      };
    },
    creatForm() {
      //updateFormOfProject  api  formData   save form - save question - save option
    },
  },
};
</script>

<style scoped></style>
