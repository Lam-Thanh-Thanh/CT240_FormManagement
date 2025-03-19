<template v-if="isLoggedIn">
  <h1 class="text-center p-20 text-4xl font-extrabold">Project Management</h1>

  <!-- Thanh tìm kiếm & Bộ lọc -->
  <div class="flex items-center space-x-4 mb-4 px-8">
    <!-- Ô tìm kiếm -->
    <div class="relative">
      <span class="absolute left-3 top-2 text-gray-400">🔍</span>
      <input 
        type="text" 
        v-model="searchQuery" 
        :placeholder="searchFilter === 'name' ? 'Search by Project Name...' : 'Search by User ID...'" 
        class="pl-10 pr-4 py-2 border rounded-full w-64"
      />
    </div>

    <!-- Bộ lọc dropdown gói gọn trong div có relative -->
<div class="relative">
  <!-- Nút mở bộ lọc -->
  <button @click="toggleFilterMenu" class="bg-gray-200 p-2 rounded-full text-gray-600 hover:bg-gray-300">
    ⚙️
  </button>

  <!-- Menu lọc (căn chỉnh vị trí ngay bên dưới) -->
  <div v-if="showFilterMenu" class="absolute top-full left-0 mt-1 bg-white border p-2 rounded shadow-md min-w-[210px]">
    <p @click="setSearchFilter('name')" class="cursor-pointer px-2 py-1 hover:bg-gray-100">
      Search by Project Name
    </p>
    <p @click="setSearchFilter('userId')" class="cursor-pointer px-2 py-1 hover:bg-gray-100">
      Search by User ID
    </p>
  </div>
</div>
  </div>

  <!-- Bảng danh sách project -->
  <div class="p-8 mb-32">
    <table class="w-full border-collapse border border-gray-300">
      <thead>
        <tr class="bg-gray-200">
          <th class="border p-2">
            Project ID 
            <button @click="sortProjects('id')" class="buttonclass ml-2 px-2 py-1 bg-gray-300 rounded">
              {{ getSortArrow('id') }}
            </button>
          </th>
          <th class="border p-2">Project Name</th>
          <th class="border p-2">
            User ID
            <button @click="sortProjects('userId')" class="buttonclass ml-2 px-2 py-1 bg-gray-300 rounded">
              {{ getSortArrow('userId') }}
            </button>
          </th>
          <th class="border p-2">
            Date Created
            <button @click="sortProjects('createdAt')" class="buttonclass ml-2 px-2 py-1 bg-gray-300 rounded">
              {{ getSortArrow('createdAt') }}
            </button>
          </th>
          <th class="border p-2">Activity</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="project in filteredProjects" :key="project.id" class="text-center">
          <td class="border p-2">{{ project.id }}</td>
          <td class="border p-2">{{ project.name }}</td>
          <td class="border p-2">{{ project.userId }}</td>
          <td class="border p-2">{{ formattedDate(project.createdAt) }}</td>
          <td class="border p-2">
            <button @click="deleteProject(project.id)" class="bg-red-500 text-white px-3 py-1 rounded">
              Xóa
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import ProjectService from "@/services/ProjectService";

export default {
  data() {
    return {
      projects: [],
      searchQuery: "",
      searchFilter: "name", // Mặc định lọc theo Project Name
      showFilterMenu: false, // Trạng thái hiển thị menu lọc
      sortKey: "",
      sortOrder: 1,
    };
  },

  async created() {
    await this.fetchAllProjects();
  },

  computed: {
    filteredProjects() {
    let filtered = this.projects.filter((project) => {
      if (!this.searchQuery) return true;

      if (this.searchFilter === "userId") {
        return project.userId && project.userId.toLowerCase().includes(this.searchQuery.toLowerCase());
      } else {
        return project.name.toLowerCase().includes(this.searchQuery.toLowerCase());
      }
    });

    // ✅ Thêm logic sắp xếp
    if (this.sortKey) {
      filtered = filtered.slice().sort((a, b) => {
        let valA = a[this.sortKey];
        let valB = b[this.sortKey];

        // Nếu là ngày, chuyển về dạng timestamp để so sánh
        if (this.sortKey === "createdAt") {
          valA = new Date(valA).getTime();
          valB = new Date(valB).getTime();
        }

        // Nếu giá trị là string, chuyển về lowercase để so sánh
        if (typeof valA === "string") valA = valA.toLowerCase();
        if (typeof valB === "string") valB = valB.toLowerCase();

        return (valA > valB ? 1 : -1) * this.sortOrder;
      });
    }

    return filtered;
  },
}
,

  methods: {
    async fetchAllProjects() {
      const response = await ProjectService.getAllProjects();
      this.projects = response.data;
    },

    async deleteProject(id) {
      if (confirm("Bạn có chắc chắn muốn xóa project này không?")) {
        try {
          await ProjectService.deleteProject(id);
          this.projects = this.projects.filter(project => project.id !== id);
          alert("Xóa project thành công!");
        } catch (error) {
          console.error("Lỗi khi xóa project:", error);
        }
      }
    },

    // Sắp xếp dữ liệu
    sortProjects(key) {
      if (this.sortKey === key) {
        this.sortOrder *= -1;
      } else {
        this.sortKey = key;
        this.sortOrder = 1;
      }
    },

    getSortArrow(key) {
      if (this.sortKey === key) {
        return this.sortOrder === 1 ? "🔼" : "🔽";
      }
      return "🔽";
    },

    // Mở menu chọn bộ lọc
    toggleFilterMenu() {
      this.showFilterMenu = !this.showFilterMenu;
    },

    // Đặt kiểu tìm kiếm
    setSearchFilter(type) {
      this.searchFilter = type;
      this.showFilterMenu = false;
    },

    formattedDate(createdAt) {
      return new Date(createdAt).toLocaleString("en-US", {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit",
        timeZoneName: "short",
      });
    }
  }
};
</script>
<style scoped>
.flex {
  display: flex;
}
.items-center {
  align-items: center;
}
.space-x-4 {
  gap: 1rem;
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
button {
  padding: 5px 10px;
  border: none;
  cursor: pointer;
  transition: 0.3s;
}
button:hover {
  background-color: #b8b8b8;
}
.bg-white {
  background-color: white;
}
.border {
  border: 1px solid #ccc;
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
button.buttonclass {
  padding: 2px 6px; 
  font-size: 10px;   
}
</style>

