<template>
  <!-- option -->
  <div class="flex flex-row justify-between items-center">
    <div class="flex flex-row items-center w-[80%]">
      <input
        type="text"
        id="optionContent"
        v-model="optionContent"
        class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
        placeholder="Text..."
      />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //text option of questionId
      optionContent: "",
    };
  },

  methods: {},
  //save without button
};
</script>

<style lang="scss" scoped></style>
