<template>
  <div class="mb-20">
    <div class="">
      <!-- question -->
      <div
        v-for="(question, index) in questions"
        :key="index"
        class="mx-5 my-20 border shadow-md shadow-myLightGray p-6 rounded"
      >
        <!-- content -->
        <div class="flex row justify-between items-start">
          <div class="w-[80%]">
            <textarea
              id="projectDescription"
              v-model="question.questionContent"
              class="w-full border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
              rows="2"
              placeholder="Question title"
            ></textarea>
          </div>

          <!-- dropdown -->
          <div class="text-right w-[20%] mx-5">
            <button
              v-on:click="question.open = !question.open"
              class="bg-zinc-200 px-2 py-1 hover:shadow-lg w-full"
            >
              Type
            </button>
            <div class="shadow-lg" v-if="question.open">
              <div
                class="hover:bg-yellow-300 px-2 py-1 border-b-gray-100 border-b-2"
              >
                <button
                  v-on:click="changeOptionType(question, 'radio')"
                  class=""
                >
                  Radio
                </button>
              </div>

              <div
                class="hover:bg-yellow-300 px-2 py-1 border-b-gray-100 border-b-2"
              >
                <button
                  v-on:click="changeOptionType(question, 'checkbox')"
                  class=""
                >
                  Check box
                </button>
              </div>
              <div
                class="hover:bg-yellow-300 px-2 py-1 border-b-gray-100 border-b-2"
              >
                <button v-on:click="changeTextType(question)" class="">
                  Text
                </button>
              </div>
              <div
                class="hover:bg-yellow-300 px-2 py-1 border-b-gray-100 border-b-2"
              >
                <button class="">File</button>
              </div>
            </div>
          </div>
        </div>
        <!-- option -->
        <div class="mt-7">
          <RadioOption
            v-if="question.showChooseOption"
            :input-type="question.type"
          ></RadioOption>
          <TextOption v-if="question.showTextOption"> </TextOption>
        </div>
      </div>
      <div class="text-center mt-10">
        <button
          v-on:click="addMoreQuestion"
          class="hover:bg-pink-800 hover:text-white text-pink-800 px-2 py-1 rounded-full border-pink-800 border transition duration-300 ease-in-out"
        >
          <i class="fa-solid fa-plus"></i>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import RadioOption from "./RadioOptions.vue";
import TextOption from "./TextOption.vue";

export default {
  components: { RadioOption, TextOption },
  data() {
    return {
      questions: [
        {
          questionContent: "",
          RadioOptions: [],
          TextOption: "",
          open: false,
          showChooseOption: false,
          showTextOption: false,
          type: "",
        },
      ],
    };
  },
  methods: {
    changeOptionType(question, type) {
      question.showChooseOption = true;
      question.showTextOption = false;
      question.type = type;
    },
    changeTextType(question) {
      question.showTextOption = true;
      question.showChooseOption = false;
    },
    addMoreQuestion() {
      this.questions.push({
        questionContent: "",
        RadioOptions: [],
        TextOption: "",
      });
    },
  },
};
</script>

<style scoped></style>
