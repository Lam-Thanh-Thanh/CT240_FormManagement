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
              class="hover:bg-blue-300 px-2 py-1 border-b-gray-100 border-b-2"
            >
              <button @click="copyLink">Sao chép liên kết</button>
            </div>

            <div
              class="hover:bg-green-300 px-2 py-1 border-b-gray-100 border-b-2"
            >
              <button @click="generateQRCode">Tạo mã QR</button>
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
    <div
      v-if="showQRCode"
      class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50"
    >
      <div class="bg-white p-6 rounded-lg shadow-lg text-center relative w-96">
        <button
          @click="closeQRCodeModal"
          class="absolute top-2 right-2 bg-red-500 text-white px-2 py-1 rounded-full"
        >
          ✖
        </button>
        <h3 class="text-xl font-bold my-4">Mã QR của bạn:</h3>
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
        <!-- file view   -->
        <div
          v-if="question.fileUrl"
          class="w-[90%] relative flex items-center gap-5"
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
              class="text-blue-600 underline"
            >
              View
            </a>
          </div>
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
            <!-- file view   -->
            <div v-if="option.fileUrl" class="w-[80%] relative">
              <iframe
                :src="option.fileUrl"
                width="100%"
                height="200px"
                class="border rounded-md"
              ></iframe>
              <!-- Nút tải xuống -->
              <div class="mt-2">
                <a
                  :href="option.fileUrl"
                  download
                  class="text-blue-600 underline"
                >
                  View
                </a>
              </div>
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
            <!-- file view   -->
            <div v-if="option.fileUrl" class="w-[80%] relative">
              <iframe
                :src="option.fileUrl"
                width="100%"
                height="200px"
                class="border rounded-md"
              ></iframe>
              <!-- Nút tải xuống -->
              <div class="mt-2">
                <a
                  :href="option.fileUrl"
                  download
                  class="text-blue-600 underline"
                >
                  View
                </a>
              </div>
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
          <div v-if="response.answers[qIndex].fileUrl" class="w-[80%] relative">
            <img
              :src="response.answers[qIndex].fileUrl"
              alt="Uploaded"
              width="100%"
            />

            <button
              type="button"
              @click="removeFile(response.answers[qIndex])"
              class="absolute -top-2 -right-2 bg-gray-300 text-black rounded-full"
            >
              <i class="fa-solid fa-xmark py-0.5 px-1.5"></i>
            </button>
          </div>

          <!-- file upload for answer -->
          <div class="">
            <label :for="'image-upload-' + question.id" class="upload-label">
              <i class="fa-regular fa-image"></i>
            </label>
            <input
              :id="'image-upload-' + question.id"
              type="file"
              @change="addFileToAnswer($event, response.answers[qIndex])"
              accept="image/*,video/*,audio/*, .pdf"
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
          fileUrl: "",
          publicId: "",
          resourceType: "",
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
    async addFileToAnswer(event, answer) {
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
        const response = await CloudinaryService.uploadFile(formData);
        const parts = response.data.split("/");
        const fileName = parts.pop().split(".")[0]; // Lấy tên file không có đuôi mở rộng

        // Xác định loại file từ MIME type
        let resourceType = "image"; // Mặc định là image
        if (file.type.startsWith("video/") || file.type.startsWith("audio/")) {
          resourceType = "video";
        }

        console.log("option: ", answer);
        answer.fileUrl = response.data;
        answer.publicId = fileName;
        answer.resourceType = resourceType; // Lưu loại file  ////////////////////////////

        console.log("Uploaded file type:", resourceType);
      } catch (error) {
        console.error("Image upload failed", error);
      }
      // Reset input file sau khi xử lý xong
      event.target.value = null;
    },
    async removeFile(answer) {
      if (!answer.fileUrl) return;

      try {
        await CloudinaryService.deleteFile(
          answer.publicId,
          answer.resourceType
        );
        answer.fileUrl = ""; // Xóa ảnh trong UI khi thành công
        answer.publicId = "";
        answer.resourceType = "";
        console.log("Image deleted successfully");
      } catch (error) {
        console.error("Error deleting image", error);
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
