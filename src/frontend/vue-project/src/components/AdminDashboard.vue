<template>
    <div class="admin-dashboard">
      <h1 class="text-3xl font-bold mb-6">Trang Quản Trị Admin</h1>
      
      <div class="flex gap-6">
        <button @click="goToUsers" class="admin-button bg-blue-500 hover:bg-blue-700">
          Quản lý User
        </button>
        <button @click="goToProjects" class="admin-button bg-green-500 hover:bg-green-700">
          Quản lý Project
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
    text-align: center;
    padding: 50px;
  }
  
  .admin-button {
    padding: 12px 24px;
    font-size: 18px;
    color: white;
    border: none;
    border-radius: 6px;
    transition: background 0.3s;
  }
  </style>
  