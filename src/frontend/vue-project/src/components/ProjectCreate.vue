<template>
  <h1 class="text-center m-32 mb-20 text-4xl font-extrabold">
    Project Creation
  </h1>
  <div class="p-6 bg-white rounded-lg shadow-md mt-0 my-40 m-80">
    <form @submit.prevent="submitForm" class="space-y-4">
      <!-- Tên Project -->
      <div>
        <label for="projectName" class="block">Name</label>
        <input
          type="text"
          id="projectName"
          v-model="form.projectName"
          class="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
          required
        />
      </div>

      <!-- Mô tả Project -->
      <div>
        <label for="projectDescription" class="block">Description</label>
        <textarea
          id="projectDescription"
          v-model="form.projectDescription"
          class="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
          rows="4"
          required
        ></textarea>
      </div>

      <!-- Nút Submit -->
      <div class="text-center">
        <button
          type="submit"
          class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          Create Project
        </button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        projectName: "",
        projectDescription: "",
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

<style scoped>
/* Bạn có thể thêm CSS tùy chỉnh ở đây nếu cần */
</style>
