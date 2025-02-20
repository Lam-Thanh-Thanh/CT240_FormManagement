<template>
  <div class="mb-60">
    <h1 class="text-center m-32 mb-20 text-4xl font-extrabold">Form Edit</h1>

    <form @submit.prevent="" class="space-y-4">
      <!-- Tên Project -->
      <div
        class="p-6 bg-white rounded-lg shadow-md mt-0 my-40 m-80 border-t-pink-800 border-t-8"
      >
        <div class="my-5 mx-3">
          <label for="formTitle" class="block font-medium">Title:</label>
          <input
            type="text"
            id="formTitle"
            v-model="form.formTitle"
            class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
            required
          />
        </div>

        <!-- Mô tả Project -->
        <div class="my-5 mx-3">
          <label for="projectDescription" class="block font-medium"
            >Description:</label
          >
          <textarea
            id="projectDescription"
            v-model="form.formDescription"
            class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
            rows="4"
            required
          ></textarea>
        </div>

        <div class="text-center mt-10">
          <button
            v-on:click="addQuestion"
            class="hover:bg-pink-800 hover:text-white text-pink-800 px-2 py-1 rounded-full border-pink-800 border transition duration-300 ease-in-out"
          >
            <i class="fa-solid fa-plus"></i>
          </button>
        </div>
      </div>
      <!-- Nút Submit -->
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        formTitle: "",
        formDescription: "",
      },
    };
  },
  methods: {
    submitForm() {
      // Xử lý dữ liệu form ở đây
      console.log("Form submitted:", this.form);
      alert("Project đã được thêm thành công!");
      this.resetForm();
    },
    resetForm() {
      this.form = {
        projectName: "",
        projectDescription: "",
      };
    },
  },
};
</script>

<style scoped></style>
