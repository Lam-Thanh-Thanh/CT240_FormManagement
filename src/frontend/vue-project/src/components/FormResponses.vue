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
        <span v-if="!form.description" class=""> No description ...</span>
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
      <!-- file view question   -->
      <div
        v-if="question.fileUrl"
        class="w-[100%] relative flex items-center gap-5"
      >
        <iframe
          :src="question.fileUrl"
          width="100%"
          height="200px"
          class="border rounded-md"
        ></iframe>
        <!-- Nút tải xuống -->
        <div class="mt-2">
          <a
            :href="question.fileUrl"
            download
            class="text-gray-900 rounded-full border border-gray-400 px-1 py-0.5 hover:bg-gray-100 transition duration-300 ease-in-out"
            title="View"
          >
            <i class="fa-solid fa-arrow-right"></i>
          </a>
        </div>
      </div>
      <!-- content -->
      <div class="flex row justify-between items-start mt-5 mb-14">
        <p class="font-semibold">{{ question.content }}</p>
      </div>

      <!-- Responses -->
      <div
        v-for="(answer, index) in getAnswersForQuestion(question.id)"
        :key="index"
        class="mt-7"
      >
        <!-- Thêm số thứ tự -->
        <p class="font-bold text-gray-500">Response {{ index + 1 }}:</p>

        <!-- user Id -->
        <p class="font-bold text-gray-500">
          User: {{ usersCache[answer.userId]?.username || "Loading..." }}
        </p>

        <!-- Nếu là câu trả lời dạng text -->
        <div v-if="answer.answerText">
          <p class="bg-gray-100 p-2 rounded my-1">{{ answer.answerText }}</p>
        </div>

        <!-- Nếu là câu trả lời checkbox -->
        <div
          v-else-if="answer.selectedOptions && answer.selectedOptions.length"
        >
          <div class="bg-gray-100 p-2 rounded my-1">
            <div
              v-for="(option, optIndex) in answer.selectedOptions"
              :key="optIndex"
            >
              <img
                v-if="option.fileUrl"
                :src="option.fileUrl"
                alt="checkbox file"
                class="w-48 h-auto rounded"
              />
              {{ option.optionContent }}
            </div>
          </div>
        </div>

        <!-- Nếu là câu trả lời dạng file -->
        <div v-else-if="answer.fileUrl">
          <div
            class="bg-gray-50 rounded my-1 flex gap-20 items-center py-4 px-40 border"
          >
            <iframe
              :src="answer.fileUrl"
              width="80%"
              height="150px"
              class="border rounded-md"
            ></iframe>
            <!-- Nút tải xuống -->
            <div class="mt-2">
              <a
                :href="answer.fileUrl"
                class="text-gray-900 rounded-full border border-gray-400 px-1 py-0.5 hover:bg-gray-100 transition duration-300 ease-in-out"
                title="View"
              >
                <i class="fa-solid fa-arrow-right"></i>
              </a>
            </div>
          </div>
        </div>
        <!-- pdf -->
        <div
          v-if="answer.textUrl"
          class="my-5 relative p-4 px-6 border rounded-full"
        >
          <a
            :href="backendUrl + answer.textUrl"
            download
            title="Download to view this file"
            class="hover:border-pink-800 hover:text-pink-800 transition duration-300 ease-in-out"
          >
            <i
              v-if="getFileIcon(answer.textUrl) === 'pdf'"
              class="fa-regular fa-file-pdf text-5xl"
            ></i>
            <i
              v-else-if="getFileIcon(answer.textUrl) === 'doc'"
              class="fa-solid fa-file-word text-5xl"
            ></i>
            <i
              v-else-if="getFileIcon(answer.textUrl) === 'xls'"
              class="fa-regular fa-file-excel text-5xl"
            ></i>
            <i
              v-else-if="getFileIcon(answer.textUrl) === 'file'"
              class="fa-regular fa-file text-5xl"
            >
              ></i
            >

            Download to view
          </a>
        </div>
        <!-- Nếu là câu trả lời radio -->
        <div v-else-if="answer.oneOption">
          <div class="bg-gray-100 p-2 rounded my-1">
            <img
              v-if="answer.oneOption.fileUrl"
              :src="answer.oneOption.fileUrl"
              alt="radio file"
              class="w-48 h-auto rounded"
            />
            {{ answer.oneOption.optionContent }}
          </div>
        </div>
        <!-- Nếu không có câu trả lời-->
        <div v-else>
          <p class="bg-gray-100 p-2 rounded my-1"><strong>No answer</strong></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FormService from "@/services/FormService";
import UserService from "@/services/UserService";

export default {
  props: ["formId"],
  data() {
    return {
      open: false,
      form: {
        questions: [],
      },
      responses: [], //all responses of form
      usersCache: {}, // Cache user

      backendUrl: "http://localhost:8080/api/files/",
    };
  },
  async created() {
    await this.getFormDetails();
    await this.getResponsesOfForm();
    // Lấy thông tin user của tất cả responses
    this.responses.forEach(async (response) => {
      await this.getUserById(response.userId);
    });
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
          answer.userId = response.userId; //
          if (answer.questionId === questionId) {
            answers.push(answer);
            console.log(answer);
          }
        });
      });

      return answers;
    },

    async getUserById(userId) {
      if (!this.usersCache[userId]) {
        const response = await UserService.getUserById(userId);
        this.usersCache[userId] = response.data; // Lưu vào cache
      }
      return this.usersCache[userId];
    },
    getFileIcon(url) {
      if (!url) return "/icons/default-file.png"; // Icon mặc định

      if (url.endsWith(".pdf")) return "pdf";
      if (url.endsWith(".doc") || url.endsWith(".docx")) return "doc";
      if (url.endsWith(".xls") || url.endsWith(".xlsx")) return "xls";

      return "file"; // Trường hợp file không xác định
    },
  },
};
</script>

<style lang="scss" scoped></style>
