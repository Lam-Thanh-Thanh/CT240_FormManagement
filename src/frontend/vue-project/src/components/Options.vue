<template>
  <!-- option -->
  <div
    v-for="(option, index) in options"
    :key="option.id"
    class="flex flex-col justify-between gap-1 mb-14"
  >
    <!-- image view-->
    <div v-if="option.imageUrl" class="w-[80%] relative">
      <img
        :src="option.imageUrl"
        alt="Uploaded"
        width="100%"
        class="rounded-md"
      />

      <button
        type="button"
        @click="removeImage(option)"
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
      <!--add image, delete button -->
      <div class="w-[20%] m-5 text-center flex justify-around">
        <div>
          <!-- Icon thay thế nút Choose File -->
          <label :for="'file-upload-' + option.id" class="upload-label">
            <i class="fa-regular fa-image"></i>
          </label>
          <input
            :id="'file-upload-' + option.id"
            type="file"
            @change="addImageToOption($event, option)"
            accept="image/*"
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

    //add image
    async addImageToOption(event, option) {
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
        console.log("option", option);
        option.imageUrl = response.data;
        option.publicId = fileName;
      } catch (error) {
        console.error("Image upload failed", error);
      }
      // Reset input file sau khi xử lý xong
      event.target.value = null;
    },

    async removeImage(option) {
      if (!option.imageUrl) return;

      try {
        await CloudinaryService.deleteImage(option.publicId);
        option.imageUrl = ""; // Xóa ảnh trong UI khi thành công
        option.publicId = "";
        console.log("Image deleted successfully");
      } catch (error) {
        console.error("Error deleting image", error);
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
