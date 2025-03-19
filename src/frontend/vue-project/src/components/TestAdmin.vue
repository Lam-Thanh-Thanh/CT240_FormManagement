<template v-if="isLoggedIn">
  <h1 class="text-center p-32 text-4xl font-extrabold">Admin Page</h1>
  <div class="p-8 mb-32">
    <table class="w-full border-collapse border border-gray-300">
      <thead>
        <tr class="bg-gray-200">
          <th class="border p-2">STT</th>
          <th class="border p-2">Tên Project</th>
          <th class="border p-2">User ID</th>
          <th class="border p-2">Ngày Tạo</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="(project, index) in projects"
          :key="project.id"
          class="text-center"
        >
          <td class="border p-2">{{ index + 1 }}</td>
          <td class="border p-2">{{ project.name }}</td>
          <td class="border p-2">{{ project.userId }}</td>
          <td class="border p-2">
            {{ formattedDate(project.createdAt) }}
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
    };
  },

  async created() {
    await this.fetchAllProjects();
  },

  methods: {
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
    },
    async fetchAllProjects() {
      const response = await ProjectService.getAllProjects();
      this.projects = response.data;
    },
  },
};
</script>

<style lang="scss" scoped></style>
