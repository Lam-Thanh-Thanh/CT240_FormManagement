<template>
    <div class="user-management">
      <h1 class="text-3xl font-bold mb-6">Quản lý Người Dùng</h1>
  
      <table class="table-auto w-full border-collapse border border-gray-300">
        <thead>
          <tr class="bg-gray-200">
            <th class="border border-gray-300 px-4 py-2">ID</th>
            <th class="border border-gray-300 px-4 py-2">Tên</th>
            <th class="border border-gray-300 px-4 py-2">Email</th>
            <th class="border border-gray-300 px-4 py-2">Vai trò</th>
            <th class="border border-gray-300 px-4 py-2">Hành động</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td class="border border-gray-300 px-4 py-2">{{ user.id }}</td>
            <td class="border border-gray-300 px-4 py-2">{{ user.username }}</td>
            <td class="border border-gray-300 px-4 py-2">{{ user.email }}</td>
            <td class="border border-gray-300 px-4 py-2">
              <select v-model="user.role" @change="updateRole(user)">
                <option value="USER">USER</option>
                <option value="ADMIN">ADMIN</option>
              </select>
            </td>
            <td class="border border-gray-300 px-4 py-2">
              <button @click="deleteUser(user.id)" class="bg-red-500 text-white px-3 py-1 rounded">
                Xóa
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  import userService from "@/services/UserService";
  
  export default {
    data() {
      return {
        users: [],
      };
    },
    async created() {
      await this.fetchUsers();
    },
    methods: {
      async fetchUsers() {
        try {
          const response = await userService.getAllUsers();
          this.users = response.data;
        } catch (error) {
          console.error("Lỗi khi tải danh sách user:", error);
        }
      },
      async updateRole(user) {
        try {
          await userService.updateUserRole(user.id, user.role);
          alert("Cập nhật vai trò thành công!");
        } catch (error) {
          console.error("Lỗi khi cập nhật vai trò:", error);
        }
      },
      async deleteUser(id) {
        if (confirm("Bạn có chắc chắn muốn xóa user này không?")) {
          try {
            await userService.deleteUser(id);
            this.users = this.users.filter(user => user.id !== id);
            alert("Xóa người dùng thành công!");
          } catch (error) {
            console.error("Lỗi khi xóa user:", error);
          }
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .user-management {
    padding: 20px;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
  }
  
  th, td {
    padding: 12px;
    text-align: left;
  }
  
  select {
    padding: 5px;
    border: 1px solid #ccc;
  }
  
  button {
    padding: 5px 10px;
    border: none;
    cursor: pointer;
  }
  </style>
  