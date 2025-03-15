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
            <!-- image view   -->
            <div v-if="question.imageUrl" class="w-[80%] relative">
              <img
                :src="question.imageUrl"
                alt="Uploaded"
                width="100%"
                class="rounded-md"
              />

              <button
                type="button"
                @click="removeImage(question)"
                class="absolute -top-2 -right-2 bg-gray-300 text-black rounded-full"
              >
                <i class="fa-solid fa-xmark py-0.5 px-1.5"></i>
              </button>
            </div>

            <!-- content -->
            <div class="flex row justify-between relative">
              <div class="w-[78%]">
                <textarea
                  id="projectDescription"
                  v-model="question.content"
                  class="w-full font-semibold border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
                  rows="2"
                  placeholder="Question title"
                ></textarea>
              </div>
              <!-- image upload for question -->
              <div class="absolute right-36">
                <label :for="'file-upload-' + question.id" class="upload-label">
                  <i class="fa-regular fa-image"></i>
                </label>
                <input
                  :id="'file-upload-' + question.id"
                  type="file"
                  @change="addImageToQuestion($event, question)"
                  accept="image/*"
                  class="hidden"
                />
              </div>

              <!-- dropdown -->
              <div class="text-center w-[18%] absolute right-0">
                <button
                  v-on:click="question.open = !question.open"
                  class="bg-white outline outline-1 outline-slate-400 rounded-sm px-2 py-2 hover:shadow-sm w-full transition duration-300 ease-in-out flex justify-between"
                  type="button"
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
                    class=""
                    type="button"
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
            <i class="fa-regular fa-trash-can"></i>
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
export default {
  components: { Options },
  props: {
    questions: Array, // Nhận danh sách câu hỏi từ Form.vue
  },
  data() {
    return {
      options: [],
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
        imageUrl: "",
        publicId: "",
      });

      console.log("Added question:", question.options);
    },

    async deleteQuestion(index) {
      this.questions.splice(index, 1);
    },
    // add image to question
    async addImageToQuestion(event, question) {
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
        console.log("question: ", question);
        question.imageUrl = response.data;
        question.publicId = fileName;
      } catch (error) {
        console.error("Image upload failed", error);
      }
      // Reset input file sau khi xử lý xong
      event.target.value = null;
    },
    async removeImage(question) {
      if (!question.imageUrl) return;

      try {
        await CloudinaryService.deleteImage(question.publicId);
        question.imageUrl = ""; // Xóa ảnh trong UI khi thành công
        question.publicId = "";
        console.log("Image deleted successfully");
      } catch (error) {
        console.error("Error deleting image", error);
      }
    },
  },
};
</script>

<style scoped></style>
