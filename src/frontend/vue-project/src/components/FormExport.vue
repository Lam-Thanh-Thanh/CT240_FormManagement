<template>
  <!-- dropdown -->
  <div class="flex gap-16 items-start mx-72 my-32 mb-20 justify-between">
    <h1 class="text-4xl font-extrabold">Form export</h1>
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
        <span class="font-bold pr-2">Create at:</span>
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
        <div class="w-[80%]">
          <p class="font-semibold">{{ question.content }}</p>
        </div>
      </div>
      <!-- option -->
      <div class="mt-16">
        <div class="flex flex-col gap-7">
          <div
            v-if="question.type === 'radio'"
            v-for="(option, oIndex) in question.options"
            :key="oIndex"
            class="flex flex-col gap-2"
          >
            <!-- image view-->
            <div v-if="option.imageUrl" class="w-[70%]">
              <img :src="option.imageUrl" alt="Uploaded" width="100%" />
            </div>
            <!-- question.type -->
            <div class="flex gap-4">
              <input
                type="radio"
                :value="option.optionContent"
                v-model="response.answers[qIndex].answerText"
              />

              <p>{{ option.optionContent }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- option -->
      <div class="mt-7">
        <div class="flex flex-col">
          <div
            v-if="question.type === 'checkbox'"
            v-for="(option, oIndex) in question.options"
            :key="oIndex"
            class="flex flex-row items-center gap-4"
          >
            <!-- question.type -->
            <input
              type="checkbox"
              :value="option.optionContent"
              v-model="response.answers[qIndex].selectedOptions"
            />

            <p>{{ option.optionContent }}</p>
          </div>
        </div>
      </div>

      <!-- text -->
      <div class="mt-7">
        <div class="flex flex-col">
          <div
            v-if="question.type === 'text'"
            class="flex flex-row items-center gap-4"
          >
            <!-- question.type -->
            <input
              type="text"
              placeholder="Text...."
              v-model="response.answers[qIndex].answerText"
            />
          </div>
        </div>
      </div>

      <!-- file -->
      <div class="mt-7">
        <div class="flex flex-col">
          <div
            v-if="question.type === 'file'"
            class="flex flex-row items-center gap-4"
          >
            <!-- question.type -->
            <!-- <input type="file" /> -->
          </div>
        </div>
      </div>
    </div>
    <!-- Nút Submit -->
    <div class="text-center">
      <button
        v-on:click="submitForm"
        type="submit"
        class="bg-pink-700 hover:bg-pink-800 text-white px-4 py-2 rounded transition duration-300 ease-in-out"
      >
        Submit
      </button>
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
      response: {
        formId: this.formId,

        answers: [],
      },
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

        console.log("🚀 API Response:", this.form); // Kiểm tra toàn bộ dữ liệu trả về
        console.log("✅ Questions:", this.form.questions); // Kiểm tra danh sách câu hỏi

        // Khởi tạo response.answers với cấu trúc đúng
        this.response.answers = this.form.questions.map((q) => ({
          responseId: this.response.id,
          questionId: q.id,
          answerText: "",
          selectedOptions: [],
        }));

        console.log(response.data);
      } catch (error) {
        console.error("There was an error getting form details:", error);
      }
    },
    async submitForm() {
      try {
        const response = await FormService.createResponse(
          this.formId,
          this.response
        );
        alert("Form submitted successfully!");
        console.log("Response saved:", response.data);
      } catch (error) {
        // console.log("errrrr", this.response);
        console.error("Error submitting response:", error);
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
