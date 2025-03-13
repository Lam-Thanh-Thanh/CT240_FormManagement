<template>
  <!-- dropdown -->
  <div class="flex gap-16 items-start mx-72 my-32 mb-20 justify-between">
    <h1 class="text-4xl font-extrabold">Form Responses</h1>
    <div class="text-right">
      <button
        v-on:click="open = !open"
        class="bg-zinc-200 rounded-full px-2 py-1 m-2 hover:shadow-lg"
      >
        <i class="fa-solid fa-ellipsis"></i>
      </button>
      <div class="shadow-lg" v-if="open">
        <div class="hover:bg-yellow-300 px-2 py-1 border-b-gray-100 border-b-2">
          <button class="">View Results</button>
        </div>

        <div class="hover:bg-zinc-200 px-2 py-1 border-b-gray-100 border-b-2">
          <button v-on:click="editForm" class="">Edit</button>
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
        <span class="font-bold pr-2">Created at:</span>
        <span class="">Date</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Update at:</span>
        <span class="">Date</span>
      </div>
    </div>

    <!-- question -->
    <div
      v-for="(question, qIndex) in form.questions"
      :key="qIndex"
      class="mx-32 my-20 shadow-lg shadow-myLightGray p-14 rounded border-t-pink-800 border-t-4"
    >
      <!-- content -->
      <div class="flex row justify-between items-start">
        <p class="font-semibold">{{ question.content }}</p>
      </div>

      <!-- Responses -->
      <div
        v-for="(answer, index) in getAnswersForQuestion(question.id)"
        :key="index"
        class="mt-7"
      >
        <p class="font-bold">Response {{ index + 1 }}:</p>
        <!-- Thêm số thứ tự -->

        <!-- Nếu là câu trả lời dạng text -->
        <div v-if="answer.answerText">
          <p class="bg-gray-100 p-2 rounded my-1">{{ answer.answerText }}</p>
        </div>

        <!-- Nếu là câu trả lời multiple-choice -->
        <div v-if="answer.selectedOptions && answer.selectedOptions.length">
          <div class="bg-gray-100 p-2 rounded my-1">
            <p
              v-for="(option, optIndex) in answer.selectedOptions"
              :key="optIndex"
            >
              {{ option }}
            </p>
          </div>
        </div>

        <!-- Nếu là câu trả lời dạng ảnh -->
        <div v-if="answer.imageUrl">
          <div class="bg-gray-100 p-2 rounded my-1">
            <img
              :src="answer.imageUrl"
              alt="User uploaded image"
              class="w-48 h-auto rounded"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FormService from "@/services/FormService";

export default {
  props: ["formId"],
  data() {
    return {
      open: false,
      form: {
        questions: [],
      },
      responses: [], //all responses of form
      // answers: [], //answers of a question
    };
  },
  async created() {
    await this.getFormDetails();
    await this.getResponsesOfForm();
  },
  methods: {
    async getFormDetails() {
      try {
        const response = await FormService.getFormDetails(this.formId);
        this.form = response.data;

        console.log("🚀 API Response:", this.form); // Kiểm tra toàn bộ dữ liệu trả về
        console.log("✅ Questions:", this.form.questions); // Kiểm tra danh sách câu hỏi
      } catch (error) {
        console.error("There was an error getting form details:", error);
      }
    },

    async getResponsesOfForm() {
      try {
        const response = await FormService.getAllResponses(this.formId);
        this.responses = response.data;

        console.log("🚀 Responses:", this.responses); // Kiểm tra toàn bộ dữ liệu trả về
      } catch (error) {
        console.error("There was an error getting responses:", error);
      }
    },

    getAnswersForQuestion(questionId) {
      let answers = [];

      this.responses.forEach((response) => {
        response.answers.forEach((answer) => {
          if (answer.questionId === questionId) {
            answers.push(answer);
          }
        });
      });

      return answers;
    },
  },
};
</script>

<style lang="scss" scoped></style>
