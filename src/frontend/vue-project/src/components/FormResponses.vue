<template>
  <div class="py-20 px-80 border">
    <div class="flex gap-16 items-start justify-between">
      <h1 class="text-4xl font-extrabold">{{ form.title }}</h1>
    </div>
    <!-- Form information -->
    <div class="text-left mt-10">
      <div class="pb-3">
        <span class="font-bold pr-2">Description:</span>
        <span class="">{{ form.description }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Created at:</span>
        <span class="">{{ formattedDate(form.createdAt) }}</span>
      </div>
    </div>
  </div>

  <div class="px-80 py-32 bg-white">
    <h1 class="text-3xl text-center font-extrabold pb-32">Responses</h1>
    <!-- question -->
    <div
      v-for="(question, qIndex) in form.questions"
      :key="qIndex"
      class="shadow-lg shadow-myLightGray p-14 rounded border-t-pink-800 border-t-4 mb-20"
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

        <!-- Nếu không có câu trả lời-->

        <!-- Nếu là câu trả lời dạng text -->
        <div v-if="answer.answerText">
          <p class="bg-gray-100 p-2 rounded my-1">{{ answer.answerText }}</p>
        </div>

        <!-- Nếu là câu trả lời multiple-choice -->
        <div
          v-else-if="answer.selectedOptions && answer.selectedOptions.length"
        >
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
        <div v-else-if="answer.imageUrl">
          <div class="bg-gray-100 p-2 rounded my-1">
            <img
              :src="answer.imageUrl"
              alt="User uploaded image"
              class="w-48 h-auto rounded"
            />
          </div>
        </div>
        <div v-else>
          <p class="bg-gray-100 p-2 rounded my-1"><strong>No answer</strong></p>
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
