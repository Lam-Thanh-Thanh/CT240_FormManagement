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
              type="button"
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
                  type="button"
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
                  type="button"
                >
                  Check box
                </button>
              </div>
              <div
                class="hover:bg-yellow-300 px-2 py-1 border-b-gray-100 border-b-2"
              >
                <button
                  v-on:click="changeTextType(question)"
                  class=""
                  type="button"
                >
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
          <div>
            <Options
              :options="question.options"
              v-if="question.showChooseOption"
              :input-type="question.type"
            ></Options>
            <!-- more option button -->
            <div>
              <button
                v-on:click="addMoreOption(question)"
                class=""
                type="button"
              >
                More option
              </button>
            </div>
          </div>

          <TextOption v-if="question.showTextOption"> </TextOption>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Options from "./Options.vue";
import TextOption from "./TextOption.vue";

export default {
  components: { Options, TextOption },
  props: {
    questions: Array, // Nhận danh sách câu hỏi từ Form.vue
  },
  data() {
    return {
      options: [],
    };
  },
  methods: {
    changeOptionType(question, type) {
      if (!question.options) {
        this.addMoreOption(question);
      }
      question.showChooseOption = true;
      question.showTextOption = false;
      question.type = type;
    },
    changeTextType(question) {
      question.showTextOption = true;
      question.showChooseOption = false;
    },
    addMoreOption(question) {
      if (!question.options) {
        question.options = [];
      }

      question.showChooseOption = true;
      question.showTextOption = false;
      question.options.push({
        optionContent: "",
        isChecked: false,
      });

      console.log("Added question:", question.options);
    },
  },
};
</script>

<style scoped></style>
