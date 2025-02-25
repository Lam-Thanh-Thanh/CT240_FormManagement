<template>
  <h1 class="text-center m-32 text-4xl font-extrabold">Project Management</h1>
  <div class="flex flex-wrap pt-0 p-28 py-40 gap-20 justify-center m-20">
    <!-- project list -->

    <button
      v-for="project in projects"
      :key="project.id"
      v-on:click="viewProjectDetails"
      class="w-[25%] bg-white shadow-lg hover:shadow-md rounded-2xl text-left transition duration-300 ease-in-out"
    >
      <div
        class="font-bold text-lg bg-pink-50 border-2 border-pink-700 rounded-t-2xl text-center py-2 px-4 overflow-hidden whitespace-nowrap overflow-ellipsis"
      >
        {{ project.name }}
      </div>
      <div class="py-5 px-4">
        <p class="line-clamp-1">
          {{ project.description }}
        </p>
      </div>
    </button>

    <!-- create new -->

    <button
      v-on:click="createProject"
      class="w-[25%] px-6 py-4 bg-white shadow-lg hover:shadow-md rounded-2xl transition duration-300 ease-in-out"
    >
      <p class="text-xl mb-2">Create new</p>
      <i class="fa-solid fa-plus"></i>
    </button>
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
    async fetchAllProjects() {
      try {
        const response = await ProjectService.getAllProjects();
        this.projects = response.data; //update variable
      } catch (error) {
        console.error("There was an error fetching the projects:", error);
      }
    },

    viewProjectDetails() {
      try {
        this.$router.push({
          name: "project-details",
        });
      } catch (error) {}
    },
    createProject() {
      try {
        this.$router.push({
          name: "project-create",
        });
      } catch (error) {}
    },
  },
};
</script>

<style lang="scss" scoped></style>
