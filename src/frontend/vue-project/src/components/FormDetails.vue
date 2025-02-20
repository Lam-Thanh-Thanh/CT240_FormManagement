<template>
  <!-- dropdown -->
  <div class="flex gap-16 items-start mx-60 my-32 justify-between">
    <h1 class="text-4xl font-extrabold">Form Details</h1>
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
          <button v-on:click="editForm" class="">Edit</button>
        </div>
        <div class="hover:bg-red-400 px-2 py-1">
          <button class="">Delete</button>
        </div>
      </div>
    </div>
  </div>

  <div class="mt-0 m-40">
    <!-- Form information -->
    <div class="text-left mx-20 my-32">
      <div class="pb-3">
        <span class="font-bold pr-2">Title:</span>
        <span class=""> {{ oldTitle }}</span>
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
        <span class="font-bold pr-2">Project Name:</span>
        <span class="">Project 1</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Create at:</span>
        <span class="">Date</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Update at:</span>
        <span class="">Date</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      open: false,
      oldTitle: "form1",
    };
  },
  methods: {
    editForm() {
      try {
        this.$router.push({
          name: "form-edit",
          query: { oldTitle: this.oldTitle },
        });
      } catch (error) {}
    },
  },
};
</script>

<style lang="scss" scoped></style>
