<template>
  <div class="">
    <!-- option -->
    <div
      v-for="(option, index) in options"
      :key="index"
      class="flex flex-row justify-between items-center"
    >
      <div class="flex flex-row items-center w-[80%]">
        <input
          :type="inputType"
          name="radioOption"
          v-model="option.isChecked"
        />

        <input
          type="text"
          id="optionContent"
          v-model="option.optionContent"
          class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
          placeholder="Text..."
        />
      </div>
      <!--add image, delete button -->
      <div class="w-[20%] m-5 text-center flex justify-around">
        <button v-on:click="addImage" class="">
          <i class="fa-regular fa-image"></i>
        </button>
        <button v-on:click="deleteOption(index)" class="">
          <i class="fa-solid fa-xmark"></i>
        </button>
      </div>
    </div>
    <!-- more option button -->
    <div>
      <button v-on:click="addMoreOption" class="">More option</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    inputType: {
      type: String,
    },
  },
  data() {
    return {
      //options of questionId
      options: [
        {
          isChecked: false,
          optionContent: "",
        },
      ],
    };
  },

  methods: {
    addMoreOption() {
      this.options.push({ optionContent: "", isChecked: false });
    },
    deleteOption(index) {
      this.options.splice(index, 1);
    },
  },
  //save without button
};
</script>

<style lang="scss" scoped></style>
