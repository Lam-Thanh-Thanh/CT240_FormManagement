<template>
  <h1 class="text-center m-32 text-4xl font-extrabold">Form Details</h1>
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
    <div class="text-center">
      <button
        v-on:click="editFormInfo"
        class="bg-yellow-400 text-black bg-opacity-85 px-3 py-1 shadow-md hover:shadow-lg mx-3"
      >
        <span class="m-2">View Results</span>
        <i class="fa-solid fa-pen-to-square"></i>
      </button>
      <button
        v-on:click="editFormInfo"
        class="bg-slate-200 hover:bg-red-700 hover:text-white bg-opacity-85 px-3 py-1 shadow-md hover:shadow-lg mx-3"
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

<script setup></script>

<style lang="scss" scoped></style>
