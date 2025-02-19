<template>
  <h1 class="text-center p-28 text-3xl font-semibold">Project Management</h1>
  <div class="flex flex-wrap pt-0 p-28 py-40 gap-14 justify-center">
    <div class="px-6 py-4 w-[20%] border-solid bg-white shadow-md rounded-3xl">
      <div class="font-bold text-xl mb-2">Project 1</div>

      <div>
        <p class="text-gray-700 text-base">
          This is a description for the project.
        </p>
      </div>

      <div class="flex justify-end gap-5 mt-1">
        <button
          class="bg-yellow-300 text-myDarkNavy px-2 py-1 rounded-full hover:bg-myGray hover:text-white shadow-lg"
        >
          <i class="fa-solid fa-right-to-bracket"></i>
        </button>
      </div>
    </div>

    <!-- create new -->
    <div
      class="flex flex-col justify-center items-center px-6 py-4 w-[15%] border-solid bg-white shadow-md hover:shadow-lg rounded-3xl"
    >
      <div class="">
        <button class="">
          <div class="text-xl mb-2">Create new</div>
          <i
            class="fa-solid fa-plus text-myDarkNavy px-2 py-1 rounded-full"
          ></i>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup></script>

<style lang="scss" scoped></style>
