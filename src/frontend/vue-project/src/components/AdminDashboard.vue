<template>
  <div class="admin-dashboard">
    <h1 class="text-center p-20 text-4xl font-extrabold ">Administration Panel</h1>

    <div class="button-container">
      <button @click="goToUsers" class="admin-button bg-blue-500 hover:bg-blue-700">
        👤 User Management
      </button>
      <button @click="goToProjects" class="admin-button bg-green-500 hover:bg-green-700">
        📂 Project Management
      </button>
    </div>
  </div>
</template>

<script>
import router from "@/router";

export default {
  methods: {
    goToUsers() {
      router.push("/admin/users");
    },
    goToProjects() {
      router.push("/admin/projects");
    },
  },
};
</script>

<style scoped>
.admin-dashboard {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
  text-align: center;
}

h1 {
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.2); /* Màu nền mờ cho tiêu đề */
}

.button-container {
  display: flex;
  gap: 20px;
}

.admin-button {
  padding: 14px 28px;
  font-size: 20px;
  font-weight: bold;
  color: white;
  border: none;
  border-radius: 8px;
  transition: transform 0.2s, background 0.3s;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
}

.admin-button:hover {
  transform: scale(1.05); /* Hiệu ứng phóng to nhẹ */
  box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.3);
}
</style>
