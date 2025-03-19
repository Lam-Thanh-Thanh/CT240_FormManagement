<template>
  <!-- option -->
  <div
    v-for="(option, index) in options"
    :key="option.id"
    class="flex flex-col justify-between gap-1 mb-14"
  >
    <!-- file view-->
    <!-- file view   -->
    <div
      v-if="option.fileUrl"
      class="w-[80%] relative flex gap-10 items-center"
    >
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
          class="text-gray-900 rounded-full border border-gray-400 p-1 hover:bg-gray-100 transition duration-300 ease-in-out"
        >
          <i class="fa-solid fa-arrow-right"></i>
        </a>
      </div>
      <button
        type="button"
        @click="removeFile(option)"
        class="absolute -top-2 -right-2 bg-gray-300 text-black rounded-full"
      >
        <i class="fa-solid fa-xmark py-0.5 px-1.5"></i>
      </button>
    </div>
    <div class="flex flex-row justify-between items-center w-[100%]">
      <div class="flex flex-row w-[100%]">
        <input :type="inputType" name="radioOption" />

        <input
          type="text"
          id="optionContent"
          v-model="option.optionContent"
          class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
          placeholder="Text..."
        />
      </div>
      <!--add File, delete button -->
      <div class="w-[20%] m-5 text-center flex justify-around">
        <!-- file upload for question -->
        <div class="">
          <label :for="'image-upload-' + option.id" class="upload-label">
            <i class="fa-regular fa-image"></i>
          </label>
          <input
            :id="'image-upload-' + option.id"
            type="file"
            @change="addFileToOption($event, option)"
            accept="image/*,video/*,audio/*, .pdf"
            class="hidden"
          />
        </div>
        <!-- delete -->
        <button v-on:click="deleteOption(index)" class="" type="button">
          <i class="fa-solid fa-xmark"></i>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import CloudinaryService from "@/services/CloudinaryService";

export default {
  props: {
    inputType: {
      type: String,
    },
    options: Array, // Nhận danh sách option từ Question.vue
  },
  data() {
    return {};
  },
  methods: {
    deleteOption(index) {
      this.options.splice(index, 1);
    },

    //add File
    // add image to question
    async addFileToOption(event, option) {
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

        console.log("option: ", option);
        option.fileUrl = response.data;
        option.publicId = fileName;
        option.resourceType = resourceType; // Lưu loại file  ////////////////////////////

        console.log("Uploaded file type:", resourceType);
      } catch (error) {
        console.error("Image upload failed", error);
      }
      // Reset input file sau khi xử lý xong
      event.target.value = null;
    },

    async removeFile(option) {
      if (!option.fileUrl) return;

      try {
        await CloudinaryService.deleteFile(
          option.publicId,
          option.resourceType
        );
        option.fileUrl = ""; // Xóa ảnh trong UI khi thành công
        option.publicId = "";
        option.resourceType = "";
        console.log("Image deleted successfully");
      } catch (error) {
        console.error("Error deleting image", error);
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
