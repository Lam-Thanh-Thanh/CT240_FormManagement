<template>
  <!-- dropdown -->
  <div class="flex gap-16 items-start mx-60 my-32 justify-between">
    <h1 class="text-4xl font-extrabold">Project Details</h1>
    <div class="text-right">
      <button
        v-on:click="open = !open"
        class="bg-zinc-200 rounded-full px-2 py-1 m-2 hover:shadow-lg"
      >
        <i class="fa-solid fa-ellipsis"></i>
      </button>
      <div class="shadow-lg" v-if="open">
        <div class="hover:bg-yellow-300 px-2 py-1 border-b-gray-100 border-b-2">
          <button class="">View Results</button>
        </div>

        <div class="hover:bg-zinc-200 px-2 py-1 border-b-gray-100 border-b-2">
          <button class="">Edit Infomation</button>
        </div>
        <div class="hover:bg-red-400 px-2 py-1">
          <button class="">Delete</button>
        </div>
      </div>
    </div>
  </div>

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
        class="w-[22%] bg-white shadow-lg hover:shadow-md rounded-2xl text-left transition duration-300 ease-in-out"
      >
        <div
          class="font-bold text-lg bg-gray-200 border-2 border-myLightNavy rounded-t-2xl text-center py-2 px-4 overflow-hidden whitespace-nowrap overflow-ellipsis"
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      open: false,
    };
  },
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
