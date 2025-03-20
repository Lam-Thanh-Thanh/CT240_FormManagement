<template>
  <div class="user-management">
    <h1 class="text-center p-20 text-4xl font-extrabold">User Management</h1>

    <!-- Search & Filter -->
    <div class="flex items-center space-x-4 px-8">
      <!-- Ô tìm kiếm -->
      <div class="relative">
        <span class="absolute left-3 top-2 text-gray-400">
          🔍
        </span>
        <input type="text" v-model="searchQuery" placeholder="Search by User Name..."
          class="pl-10 pr-4 py-2 border rounded-full w-64" />
      </div>

      <!-- Bộ lọc dropdown gói gọn trong div có relative -->
      <div class="relative">
        <!-- Nút mở bộ lọc -->
        <button @click="toggleRoleFilter" class="bg-gray-200 p-2 rounded-full text-gray-600 hover:bg-gray-300">
          ⚙️
        </button>

        <!-- Menu lọc (căn chỉnh vị trí ngay bên dưới) -->
        <div v-if="showRoleFilter"
          class="absolute top-full left-0 mt-1 bg-white border p-2 rounded shadow-md min-w-[210px]">
          <p @click="setRoleFilter('')" class="cursor-pointer px-2 py-1 hover:bg-gray-100">
            All roles
          </p>
          <p @click="setRoleFilter('USER')" class="cursor-pointer px-2 py-1 hover:bg-gray-100">
            USER
          </p>
          <p @click="setRoleFilter('ADMIN')" class="cursor-pointer px-2 py-1 hover:bg-gray-100">
            ADMIN
          </p>
        </div>
      </div>
    </div>

    <div class="p-8 mb-32">
      <table class="table-auto w-full border-collapse border border-gray-300">
      <thead>
        <tr class="bg-gray-200">
          <th class="border border-gray-300 px-4 py-2">Avatar</th>
          <th class="border border-gray-300 px-4 py-2">
            ID
            <button @click="sortUsers('id')" class="buttonclass ml-2 px-2 py-1 bg-gray-300 rounded">{{
              getSortArrow('id') }}</button>
          </th>
          <th class="border border-gray-300 px-4 py-2">
            User Name
            <button @click="sortUsers('username')" class="buttonclass ml-2 px-2 py-1 bg-gray-300 rounded">{{
              getSortArrow('username') }}</button>
          </th>
          <th class="border border-gray-300 px-4 py-2">
            Full Name
            <button @click="sortUsers('fullName')" class="buttonclass ml-2 px-2 py-1 bg-gray-300 rounded">{{
              getSortArrow('fullName') }}</button>
          </th>
          <th class="border border-gray-300 px-4 py-2">Email</th>
          <th class="border border-gray-300 px-4 py-2">Role</th>
          <th class="border border-gray-300 px-4 py-2">Activity</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in filteredUsers" :key="user.id">
          <td class="border border-gray-300 px-4 py-2">
            <img :src="user.avatar || defaultAvatar" alt="Avatar" class="w-10 h-10 rounded-full object-cover" />
          </td>
          <td class="border border-gray-300 px-4 py-2">{{ user.id }}</td>
          <td class="border border-gray-300 px-4 py-2">{{ user.username }}</td>
          <td class="border border-gray-300 px-4 py-2">{{ user.fullName }}</td>
          <td class="border border-gray-300 px-4 py-2">{{ user.email }}</td>
          <td class="border border-gray-300 px-4 py-2">
            <select v-model="user.role" @change="updateRole(user)">
              <option value="USER">USER</option>
              <option value="ADMIN">ADMIN</option>
            </select>
          </td>
          <td class="border border-gray-300 px-4 py-2">
            <button @click="deleteUser(user.id)" class="bg-red-500 text-white px-3 py-1 rounded">
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>
    </div>
    
  </div>
</template>


<script>
import userService from "@/services/UserService";
import defaultAvatarImg from "@/assets/shiba-smug.gif";

export default {
  data() {
    return {
      users: [],
      sortKey: "",
      sortOrder: 1,
      searchQuery: "",
      selectedRole: "",
      showRoleFilter: false, // Trạng thái hiển thị bộ lọc role
      defaultAvatar: defaultAvatarImg,
    };
  },
  async created() {
    await this.fetchUsers();
  },
  computed: {
    filteredUsers() {
      return this.sortedUsers.filter(user => {
        const matchesSearch = user.username.toLowerCase().includes(this.searchQuery.toLowerCase());
        const matchesRole = this.selectedRole ? user.role === this.selectedRole : true;
        return matchesSearch && matchesRole;
      });
    },
    sortedUsers() {
      if (!this.sortKey) return this.users;
      return [...this.users].sort((a, b) => {
        if (a[this.sortKey] > b[this.sortKey]) return this.sortOrder;
        if (a[this.sortKey] < b[this.sortKey]) return -this.sortOrder;
        return 0;
      });
    }
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
        alert("Role update successful!");
      } catch (error) {
        console.error("Lỗi khi cập nhật vai trò:", error);
      }
    },
    async deleteUser(id) {
      if (confirm("Are you sure you want to delete this user?")) {
        try {
          await userService.deleteUser(id);
          this.users = this.users.filter(user => user.id !== id);
          alert("User deleted successfully!");
        } catch (error) {
          console.error("Lỗi khi xóa user:", error);
        }
      }
    },
    sortUsers(key) {
      if (this.sortKey === key) {
        this.sortOrder *= -1;
      } else {
        this.sortKey = key;
        this.sortOrder = 1;
      }
    },
    toggleRoleFilter() {
      this.showRoleFilter = !this.showRoleFilter;
    },
    setRoleFilter(role) {
      this.selectedRole = role;
      this.showRoleFilter = false; // Đóng menu sau khi chọn
    },
    // Hiển thị mũi tên 🔽🔼
    getSortArrow(key) {
      if (this.sortKey === key) {
        return this.sortOrder === 1 ? "🔼" : "🔽";
      }
      return "🔽"; // Mặc định là giảm dần
    }
  },
};
</script>

<style scoped>
.flex {
  display: flex;
}

.items-center {
  align-items: center;
}

.justify-between {
  justify-content: space-between;
}

.mb-4 {
  margin-bottom: 1rem;
}

input,
select {
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-right: 10px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 12px;
  text-align: left;
}

button {
  padding: 5px 10px;
  border: none;
  cursor: pointer;
  transition: 0.3s;
}

button:hover {
  background-color: #b8b8b8;
}

button.buttonclass {
  padding: 2px 6px;
  font-size: 10px;
}

.relative {
  position: relative;
}

.absolute {
  position: absolute;
}

.left-3 {
  left: 0.75rem;
}

.pl-10 {
  padding-left: 2.5rem;
}

.pr-4 {
  padding-right: 1rem;
}

.rounded-full {
  border-radius: 9999px;
}

.p-2 {
  padding: 0.5rem;
}

.rounded {
  border-radius: 5px;
}

.shadow-md {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.cursor-pointer {
  cursor: pointer;
}

.hover\:bg-gray-100:hover {
  background-color: #f7f7f7;
}
</style>