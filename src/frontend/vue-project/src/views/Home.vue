<template>
  <div
    class="flex h-[30vh] lg:h-[60vh] justify-center items-center bg-myGray1 px-40 gap-48"
  >
    <div>
      <p class="text-3xl lg:text-7xl font-extrabold">Welcome!</p>
    </div>

    <div>
      <img
        src="../assets/undraw_customer-survey_2l89.svg"
        alt="welcome-img"
        class="w-[100%] h-[100%]"
      />
    </div>
  </div>

  <div>
    <ProjectList />
  </div>
</template>

<script setup>
import ProjectList from "@/components/ProjectList.vue";
</script>

<style lang="scss" scoped></style>
