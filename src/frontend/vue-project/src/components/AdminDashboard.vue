<template>
  <div class="container mx-auto p-6">
    <h1 class="text-center p-32 text-4xl font-extrabold text-gray-800">User management</h1>

    <div class="overflow-x-auto">
      <table class="w-full bg-white border border-gray-200 rounded-lg shadow-md">
        <thead class="bg-gray-100 text-gray-700 uppercase text-sm">
          <tr>
            <th class="px-4 py-3 text-left">ID</th>
            <th class="px-4 py-3 text-left">UserName</th>
            <th class="px-4 py-3 text-left">Role</th>
            <th class="px-4 py-3 text-center">Activity</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="user in users"
            :key="user.id"
            class="border-b hover:bg-gray-50 transition-all"
          >
            <td class="px-4 py-3">{{ user.id }}</td>
            <td class="px-4 py-3">{{ user.username }}</td>
            <td class="px-4 py-3">

              <select
                v-model="user.roles"
                @change="updateRole(user)"
                class="border border-gray-300 rounded px-2 py-1 text-gray-700 focus:ring focus:ring-blue-300 focus:border-blue-500 transition"
                :style="{
                  backgroundColor: user.roles === 'ADMIN' ? '#bfdbfe' : '#bbf7d0', // Xanh dương cho ADMIN, xanh lá cho USER
                  color: user.roles === 'ADMIN' ? '#1e3a8a' : '#065f46', // Màu chữ tương phản với nền
                }"
              >
                <option value="USER">USER</option>
                <option value="ADMIN">ADMIN</option>
              </select>
            </td>
            <td class="px-4 py-3 text-center">
              <button
                @click="deleteUser(user.id)"
                class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 transition-all"
              >
                Xóa
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import AdminService from "@/services/AdminService";

export default {
  data() {
    return {
      users: [],
    };
  },
  async created() {
    this.loadUsers();
  },
  methods: {
    async loadUsers() {
      try {
        const response = await AdminService.getAllUsers();
        this.users = response.data.map(user => ({
          ...user,
          roles: user.roles[0] || "USER"  // Gán mặc định nếu không có giá trị
        }));
      } catch (error) {
            console.error("Lỗi khi tải danh sách người dùng:", error);
      }
    },
    async updateRole(user) {
      try {
        await AdminService.updateUserRole(user.id, user.roles);
        alert("Cập nhật quyền thành công!");
      } catch (error) {
        console.error("Lỗi khi cập nhật quyền:", error);
      }
    },
    async deleteUser(id) {
      if (!confirm("Bạn có chắc chắn muốn xóa người dùng này?")) return;
      try {
        await AdminService.deleteUser(id);
        this.loadUsers();
      } catch (error) {
        console.error("Lỗi khi xóa người dùng:", error);
      }
    },
  },
};
</script>
