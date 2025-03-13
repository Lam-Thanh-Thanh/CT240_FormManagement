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
        <span class="font-bold pr-2">Created at:</span>
        <span class="">{{ formattedDate(form.createdAt) }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Updated at:</span>
        <span class="">{{ formattedDate(form.lastModifiedAt) }}</span>
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
      <!--radio option -->

      <div class="mt-16 flex flex-col gap-7">
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

      <!--checkbox option -->

      <div class="mt-7 flex flex-col gap-7">
        <div
          v-if="question.type === 'checkbox'"
          v-for="(option, oIndex) in question.options"
          :key="oIndex"
          class="flex flex-col items-start gap-2"
        >
          <!-- image view-->
          <div v-if="option.imageUrl" class="w-[70%]">
            <img :src="option.imageUrl" alt="Uploaded" width="100%" />
          </div>
          <!-- question.type -->
          <div class="flex gap-4">
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

      <div class="mt-7 flex flex-col">
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

      <!-- file -->

      <div
        v-if="question.type === 'file'"
        class="flex flex-row items-center gap-4 justify-between"
      >
        <!-- image view-->
        <div v-if="response.answers[qIndex].imageUrl" class="w-[80%] relative">
          <img
            :src="response.answers[qIndex].imageUrl"
            alt="Uploaded"
            width="100%"
          />

          <button
            type="button"
            @click="removeImage(response.answers[qIndex])"
            class="absolute -top-2 -right-2 bg-gray-300 text-black rounded-full"
          >
            <i class="fa-solid fa-xmark py-0.5 px-1.5"></i>
          </button>
        </div>
        <!--add image, delete button -->
        <div>
          <!-- Icon thay thế nút Choose File -->
          <label :for="image - answer" class="upload-label">
            <i class="fa-regular fa-image"></i>
          </label>
          <input
            :id="image - answer"
            type="file"
            @change="addImageAnswer($event, response.answers[qIndex])"
            accept="image/*"
            class="hidden"
          />
        </div>
      </div>
    </div>
    <!-- Nút Submit -->
    <div class="text-center">
      <button
        v-on:click="submitForm()"
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
import CloudinaryService from "@/services/CloudinaryService";
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

        // Khởi tạo response.answers với cấu trúc đúng
        this.response.answers = this.form.questions.map((q) => ({
          questionId: q.id,
          answerText: "",
          selectedOptions: [],
          imageUrl: "",
          publicId: "",
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

    // add image answer
    async addImageAnswer(event, answer) {
      const file = event.target.files[0];

      if (!file) {
        console.error("No file selected");
        return;
      }

      const formData = new FormData();
      formData.append("file", file);
      console.log("Uploading file:", file);
      console.log("FormData content:", formData.get("file")); // Kiểm tra dữ liệu gửi đi

      try {
        const response = await CloudinaryService.uploadImage(formData);
        const parts = response.data.split("/");
        const fileName = parts.pop().split(".")[0]; // Lấy tên file không có đuôi mở rộng
        console.log("answer", answer);
        answer.imageUrl = response.data;
        answer.publicId = fileName;
      } catch (error) {
        console.error("Image upload failed", error);
      }
      // Reset input file sau khi xử lý xong
      event.target.value = null;
    },
    async removeImage(answer) {
      if (!answer.imageUrl) return;

      try {
        await CloudinaryService.deleteImage(answer.publicId);
        answer.imageUrl = ""; // Xóa ảnh trong UI khi thành công
        answer.publicId = "";
        console.log("Image deleted successfully");
      } catch (error) {
        console.error("Error deleting image", error);
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
