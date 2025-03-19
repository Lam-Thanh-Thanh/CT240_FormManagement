<template>
  <div v-if="!submitted">
    <div class="py-20 px-80 border">
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
          <div class="shadow-lg" v-if="open">
            <div
              class="hover:bg-yellow-300 px-2 py-1 border-b-gray-100 border-b-2"
            >
              <button class="">button</button>
            </div>
            <div class="hover:bg-blue-300 px-2 py-1 border-b-gray-100 border-b-2">
              <button @click="copyLink">Copy link</button>
            </div>

            <div class="hover:bg-green-300 px-2 py-1 border-b-gray-100 border-b-2">
              <button @click="generateQRCode">Create QR code</button>
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

      <!-- Thanh -->
    <!-- Modal hiển thị QR Code -->
    <div v-if="showQRCode" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div class="bg-white p-6 rounded-lg shadow-lg text-center relative w-96">
        <button @click="closeQRCodeModal" class="absolute top-2 right-2 bg-red-500 text-white px-2 py-1 rounded-full">
          ✖
        </button>
        <h3 class="text-xl font-bold my-4">Your QR code:</h3>
        <img :src="qrCode" alt="QR Code" class="w-40 h-40 mx-auto" />
      </div>
    </div>
      <!-- Thanh -->

    <div class="py-40 bg-white">
      <!-- question -->
      <div
        v-for="(question, qIndex) in form.questions"
        :key="question.id"
        class="mx-80 mb-20 shadow-myLightGray shadow-md border- p-14 rounded hover:border-t-4 hover:border-t-pink-800 hover:border hover:border-pink-800 border-t-pink-800 border-t-4 transition duration-300 ease-in-out"
      >
        <!-- image view-->
        <div v-if="question.imageUrl" class="w-[80%] relative">
          <img
            :src="question.imageUrl"
            alt="Uploaded"
            width="100%"
            class="rounded-md"
          />
        </div>
        <!-- content -->
        <div class="flex row justify-between items-start my-5">
          <div class="w-[80%]">
            <p class="font-semibold">{{ question.content }}</p>
          </div>
        </div>
        <!--radio option -->

        <div class="mt-16 flex flex-col gap-7">
          <div
            v-if="question.type === 'radio'"
            v-for="(option, oIndex) in question.options"
            :key="option.id"
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
                :value="option"
                v-model="response.answers[qIndex].oneOption"
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
            :key="option.id"
            class="flex flex-col items-start gap-2"
          >
            <!-- image view-->
            <div v-if="option.imageUrl" class="w-[70%]">
              <img
                :src="option.imageUrl"
                alt="Uploaded"
                width="100%"
                class="rounded-md"
              />
            </div>
            <!-- question.type -->
            <div class="flex gap-4">
              <input
                type="checkbox"
                :value="option"
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
              class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
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
          <div
            v-if="response.answers[qIndex].imageUrl"
            class="w-[80%] relative"
          >
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
  </div>
  <ThanksForSubmit v-if="submitted"></ThanksForSubmit>
</template>

<script>
import FormService from "@/services/FormService";
import CloudinaryService from "@/services/CloudinaryService";
// THANH
import QRCode from "qrcode";
//THANH
import ThanksForSubmit from "./ThanksForSubmit.vue";
import router from "@/router"; // Import router để điều hướng
import { jwtDecode } from "jwt-decode";
export default {
  props: ["formId"],
  components: { ThanksForSubmit },
  data() {
    return {
      open: false,
      form: {
        questions: [],
      },
      response: {
        formId: this.formId,
        userId: "",
        answers: [],
      },
      submitted: false,
      currentLink: `http://localhost:5173/${this.formId}`, // Link gốc
      qrCode: "", // Ảnh QR Code
      showQRCode: false, // Biến kiểm soát hiển thị QR Code
    };
  },
  async created() {
    await this.getFormDetails();
  },
  methods: {
    // THANH
     // Sao chép liên kết
      copyLink() {
        navigator.clipboard.writeText(this.currentLink).then(() => {
          alert("Đã sao chép liên kết!");
        });
      },

      // Tạo mã QR và hiển thị ảnh
      async generateQRCode() {
        try {
          const qrData = await QRCode.toDataURL(this.currentLink);
          this.qrCode = qrData;
          this.showQRCode = true; // Hiển thị modal
        } catch (error) {
          console.error("Lỗi khi tạo mã QR:", error);
        }
      },
      closeQRCodeModal() {
        this.showQRCode = false; // Đóng modal
      },
    // THANH
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
          oneOption: {},
          selectedOptions: [],
          imageUrl: "",
          publicId: "",
        }));

        console.log(response.data);
      } catch (error) {
        console.error("There was an error getting form details:", error);
      }
    },

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
        this.response.userId = decoded.sub; // Đảm bảo key trong token là 'sub' hoặc 'userId'
        return true;
      } catch (error) {
        console.error("Lỗi khi kiểm tra đăng nhập:", error);
        alert("Đã xảy ra lỗi, vui lòng thử lại!");
        router.push("/login");
        return false;
      }
    },

    async submitForm() {
      //check login
      // Đợi kết quả từ checkLogin()
      const isLoggedIn = await this.checkLogin();
      if (!isLoggedIn) return;

      //get userId
      try {
        const response = await FormService.createResponse(
          this.formId,
          this.response
        );
        alert("Form is submitted successfully!");
        this.resetResponse();
        this.submitted = true;
        console.log("Submitted status:", this.submitted); // Kiểm tra trạng thái
        console.log("Response saved:", response.data);
      } catch (error) {
        // console.log("errrrr", this.response);
        console.error("Error submitting response:", error);
      }
    },
    resetResponse() {
      // Khởi tạo response.answers với cấu trúc đúng
      this.response.answers = this.form.questions.map((q) => ({
        questionId: q.id,
        answerText: "",
        oneOption: {},
        selectedOptions: [],
        imageUrl: "",
        publicId: "",
      }));
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
