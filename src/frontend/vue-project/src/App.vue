<template>
  <div class="flex flex-col min-h-screen font-Georama text-myDarkNavy">
    <Navbar />
    <div class="flex-grow bg-gradient-to-b from-white to-gray-200">
      <RouterView />
    </div>
    <Footer></Footer>
  </div>
</template>

<script setup>
import { RouterView } from "vue-router";
import Navbar from "./components/Navbar.vue";
import Footer from "./components/Footer.vue";
</script>
<style></style>
