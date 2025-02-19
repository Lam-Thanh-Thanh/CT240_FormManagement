<template>
  <h1 class="text-center m-32 text-4xl font-extrabold">Project Management</h1>
  <div class="flex flex-wrap pt-0 p-28 py-40 gap-14 justify-center">
    <!-- project list -->

    <button
      v-on:click="viewProjectDetails"
      class="w-[18%] bg-white shadow-md hover:shadow-lg rounded-2xl text-left"
    >
      <div
        class="font-bold text-lg bg-yellow-400 rounded-t-2xl text-center py-2 px-4 overflow-hidden whitespace-nowrap overflow-ellipsis"
      >
        Project 12
      </div>
      <div class="py-3 px-4">
        <p class="overflow-hidden whitespace-nowrap overflow-ellipsis">
          Lorem ipsum dolor sit amet.
        </p>
        <p class="">Date:</p>
      </div>
    </button>
    <!-- create new -->

    <button
      v-on:click="createProject"
      class="w-[15%] px-6 py-4 bg-white shadow-md hover:shadow-lg rounded-2xl"
    >
      <p class="text-xl mb-2">Create new</p>
      <i class="fa-solid fa-plus"></i>
    </button>
  </div>
</template>

<script>
export default {
  methods: {
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
