<template>
  <div class="mb-20">
    <div
      v-for="(question, qindex) in questions"
      :key="qindex"
      class="flex flex-col"
    >
      <div class="flex flex-row">
        <!-- question -->

        <div
          class="w-[95%] mx-5 my-10 shadow-myLightGray shadow-md hover:border hover:border-pink-800 hover:border-l-4 hover:borde-l-pink-800 border-l-pink-800 border-l-4 transition duration-300 ease-in-out rounded flex flex-row items-center justify-between p-10"
        >
          <div class="w-[100%]">
            <!-- file view   -->
            <div
              v-if="question.fileUrl"
              class="w-[90%] relative flex gap-10 items-center"
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
                  class="text-gray-900 rounded-full border border-gray-400 p-1 hover:bg-gray-100 transition duration-300 ease-in-out"
                >
                  <i class="fa-solid fa-arrow-right"></i>
                </a>
              </div>
              <button
                type="button"
                @click="removeFile(question)"
                class="absolute -top-2 -right-2 bg-gray-300 text-black rounded-full"
              >
                <i class="fa-solid fa-xmark py-0.5 px-1.5"></i>
              </button>
            </div>
            <!-- pdf -->
            <div v-if="question.textUrl">
              <a
                :href="backendUrl + question.textUrl"
                download
                title="Download to view this file"
              >
                <i
                  v-if="getFileIcon(question.textUrl) === 'pdf'"
                  class="fa-regular fa-file-pdf text-5xl"
                ></i>
                <i
                  v-else-if="getFileIcon(question.textUrl) === 'doc'"
                  class="fa-solid fa-file-word text-5xl"
                ></i>
                <i
                  v-else-if="getFileIcon(question.textUrl) === 'xls'"
                  class="fa-regular fa-file-excel text-5xl"
                ></i>
                <i
                  v-else-if="getFileIcon(question.textUrl) === 'file'"
                  class="fa-regular fa-file text-5xl"
                >
                  ></i
                >

                Download to view
              </a>
            </div>
            <!-- content -->
            <div class="flex row justify-between relative my-10">
              <div class="w-[78%]">
                <textarea
                  id="projectDescription"
                  v-model="question.content"
                  class="w-full font-semibold border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
                  rows="2"
                  placeholder="Question title"
                  required
                ></textarea>
              </div>
              <!-- file upload for question -->
              <div class="absolute right-36">
                <label
                  :for="'image-upload-' + question.id"
                  class="upload-label"
                >
                  <i class="fa-regular fa-image" title="Upload image file"></i>
                </label>
                <input
                  :id="'image-upload-' + question.id"
                  type="file"
                  @change="addFileToQuestion($event, question)"
                  accept="image/*,video/*,audio/*"
                  class="hidden"
                />
              </div>
              <!-- pdf upload for question ----------------------------------------------------->
              <div class="absolute right-44">
                <label :for="'file-upload-' + question.id" class="upload-label">
                  <i class="fa-solid fa-upload" title="Upload text file"></i>
                </label>
                <input
                  :id="'file-upload-' + question.id"
                  type="file"
                  @change="uploadPdf($event, question)"
                  accept=".doc,.pdf,.xlsx"
                  class="hidden"
                />
              </div>
              <!-- dropdown -->
              <div class="text-center w-[18%] absolute right-0">
                <button
                  v-on:click="question.open = !question.open"
                  class="bg-white outline outline-1 outline-slate-400 rounded-sm px-2 py-2 hover:shadow-sm w-full transition duration-300 ease-in-out flex justify-between"
                  type="button"
                  title="Choose question type"
                >
                  Type
                  <i class="fa-solid fa-caret-down"></i>
                </button>
                <div class="shadow-lg" v-if="question.open">
                  <div
                    class="bg-white hover:bg-gray-100 px-2 py-2 border-b-gray-100"
                  >
                    <button
                      v-on:click="changeOptionType(question, 'radio')"
                      class=""
                      type="button"
                    >
                      Radio
                    </button>
                  </div>

                  <div
                    class="bg-white hover:bg-gray-100 px-2 py-2 border-b-gray-100"
                  >
                    <button
                      v-on:click="changeOptionType(question, 'checkbox')"
                      class=""
                      type="button"
                    >
                      Check box
                    </button>
                  </div>
                  <div
                    class="bg-white hover:bg-gray-100 px-2 py-2 border-b-gray-100"
                  >
                    <button
                      v-on:click="changeOtherType(question, 'text')"
                      class=""
                      type="button"
                    >
                      Text
                    </button>
                  </div>
                  <div
                    class="bg-white hover:bg-gray-100 px-2 py-2 border-b-gray-100"
                  >
                    <button
                      v-on:click="changeOtherType(question, 'file')"
                      class=""
                      type="button"
                    >
                      File
                    </button>
                  </div>
                </div>
              </div>
            </div>
            <!-- type -->
            <div class="mt-5">
              <!-- option -->
              <div
                v-if="question.type === 'radio' || question.type === 'checkbox'"
              >
                <Options
                  :options="question.options"
                  :input-type="question.type"
                  v-model="question.options"
                ></Options>
                <!-- more options -->
                <div>
                  <button
                    v-on:click="addMoreOption(question)"
                    class="px-1 border border-gray-600 rounded-sm hover:bg-gray-100 transition duration-300 ease-in-out"
                    type="button"
                    title="Add more option"
                  >
                    More option
                  </button>
                </div>
              </div>
              <!-- text -->
              <div v-if="question.type === 'text'">
                <div class="flex flex-row justify-between items-center">
                  <div class="flex flex-row items-center w-[80%]">
                    <input
                      type="text"
                      class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
                      placeholder="Text..."
                    />
                  </div>
                </div>
              </div>
              <!-- file -->
              <div v-if="question.type === 'file'">
                <div class="flex flex-row justify-between items-center">
                  <div class="flex flex-row items-center w-[80%]">
                    <input
                      type="file"
                      class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
                      placeholder="Text..."
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- delete question -->
        <div class="w-[5%] flex flex-row items-center justify-around">
          <button v-on:click="deleteQuestion(qindex)" class="" type="button">
            <i class="fa-regular fa-trash-can" title="Remove question"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Options from "./Options.vue";
import { v4 as uuidv4 } from "uuid";
import CloudinaryService from "@/services/CloudinaryService";
import axios from "axios";
export default {
  components: { Options },
  props: {
    questions: Array, // Nhận danh sách câu hỏi từ Form.vue
  },
  data() {
    return {
      options: [],
      backendUrl: "http://localhost:8080/api/files/",
    };
  },
  methods: {
    changeOptionType(question, type) {
      if (!question.options) {
        this.addMoreOption(question);
      }

      question.type = type;
    },
    changeOtherType(question, type) {
      question.options = [];

      question.type = type;
    },

    async addMoreOption(question) {
      if (!question.options) {
        question.options = [];
      }

      question.options.push({
        id: uuidv4(),
        questionId: question.id,
        optionContent: "",
        fileUrl: "",
        publicId: "",
        resourceType: "",
        textUrl: "",
      });

      console.log("Added question:", question.options);
    },

    async deleteQuestion(index) {
      if (this.questions.length > 1) {
        this.questions.splice(index, 1);
        console.log("Deleted question:", this.questions);
      } else {
        alert("The form must have at least one question!");
      }
    },
    // add image to question
    async addFileToQuestion(event, question) {
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

        console.log("question: ", question);
        question.fileUrl = response.data;
        question.publicId = fileName;
        question.resourceType = resourceType; // Lưu loại file  ////////////////////////////

        console.log("Uploaded file type:", resourceType);
      } catch (error) {
        console.error("Image upload failed", error);
      }
      // Reset input file sau khi xử lý xong
      event.target.value = null;
    },

    async removeFile(question) {
      if (!question.fileUrl) return;

      try {
        await CloudinaryService.deleteFile(
          question.publicId,
          question.resourceType
        );
        question.fileUrl = ""; // Xóa ảnh trong UI khi thành công
        question.publicId = "";
        question.resourceType = "";
        console.log("Image deleted successfully");
      } catch (error) {
        console.error("Error deleting image", error);
      }
    },

    // add pdf to question --------------------------------------------------------------------------
    async uploadPdf(event, question) {
      const file = event.target.files[0];
      if (!file) return;

      const formData = new FormData();
      formData.append("file", file);

      try {
        const response = await axios.post(
          "http://localhost:8080/api/files/upload",
          formData,
          {
            headers: { "Content-Type": "multipart/form-data" },
          }
        );

        question.textUrl = response.data;
      } catch (error) {
        console.error("Error uploading PDF", error);
      }
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

<style scoped></style>
