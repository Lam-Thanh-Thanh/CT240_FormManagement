<template>
  <h1 class="text-center m-32 text-4xl font-extrabold">Project Details</h1>

  <div class="mt-0 m-40">
    <!-- project information -->
    <div class="text-left mx-20 my-32">
      <div class="pb-3">
        <span class="font-bold pr-2">Name:</span>
        <span class="">Project 1</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Description:</span>
        <span class=""
          >Lorem, ipsum dolor sit amet consectetur adipisicing elit. Rerum
          libero eaque omnis quia dignissimos, fugiat praesentium illum
          excepturi temporibus corporis.</span
        >
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Date created:</span>
        <span class="">Date</span>
      </div>
    </div>

    <!-- form list -->
    <div class="flex flex-wrap gap-14 justify-start mx-20 mt-32 mb-48">
      <button
        v-on:click="viewFormDetails"
        class="w-[22%] bg-white shadow-md hover:shadow-lg rounded-2xl text-left"
      >
        <div
          class="font-bold text-lg bg-slate-300 rounded-t-2xl text-center py-2 px-4 overflow-hidden whitespace-nowrap overflow-ellipsis"
        >
          form 1
        </div>
        <div class="py-3 px-4">
          <p class="overflow-hidden whitespace-nowrap overflow-ellipsis">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Odio,
            ipsum?
          </p>
          <p class="">Date:</p>
        </div>
      </button>

      <!-- create new -->

      <button
        v-on:click="addForm"
        class="w-[15%] px-6 py-4 bg-white shadow-md hover:shadow-lg rounded-2xl"
      >
        <p class="text-xl mb-2">Add form</p>
        <i class="fa-solid fa-plus"></i>
      </button>
    </div>
    <!-- edit delete -->
    <div class="text-center">
      <button
        v-on:click="editFormInfo"
        class="bg-yellow-400 text-black bg-opacity-85 px-3 py-1 shadow-md hover:shadow-lg mx-3"
      >
        <span class="m-2">Edit</span>
        <i class="fa-solid fa-pen-to-square"></i>
      </button>
      <button
        v-on:click="editFormInfo"
        class="bg-slate-200 hover:bg-red-700 hover:text-white bg-opacity-85 px-3 py-1 shadow-md hover:shadow-lg mx-3"
      >
        <span class="m-2">Delete</span>
        <i class="fa-solid fa-trash"></i>
      </button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    viewFormDetails() {
      try {
        this.$router.push({
          name: "form-details",
        });
      } catch (error) {}
    },
    addForm() {
      try {
        this.$router.push({
          name: "form-create",
        });
      } catch (error) {}
    },
  },
};
</script>

<style lang="scss" scoped></style>
