<template>
  <div class="mb-20">
    <div class="">
      <!-- question -->

      <div
        v-for="question in questions"
        :key="question.id"
        class="mx-5 my-20 border-l-pink-800 border-l-4 shadow-md hover:shadow-sm shadow-myLightGray transition duration-300 ease-in-out rounded flex flex-row items-center justify-between p-7 pl-9"
      >
        <div class="w-[90%]">
          <!-- content -->
          <div class="flex row justify-between items-start">
            <div class="w-[80%]">
              <textarea
                id="projectDescription"
                v-model="question.content"
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
                    v-on:click="changeOtherType(question, 'text')"
                    class=""
                    type="button"
                  >
                    Text
                  </button>
                </div>
                <div
                  class="hover:bg-yellow-300 px-2 py-1 border-b-gray-100 border-b-2"
                >
                  <button
                    v-on:click="changeOtherType(question, 'file')"
                    class=""
                    type="button"
                  >
                    File
                  </button>
                </div>
              </div>
            </div>
          </div>
          <!-- type -->
          <div class="mt-7">
            <!-- option -->
            <div
              v-if="question.type === 'radio' || question.type === 'checkbox'"
            >
              <Options
                :options="question.options"
                :input-type="question.type"
                v-model="question.options"
              ></Options>
              <!-- more options -->
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
            <!-- text -->
            <div v-if="question.type === 'text'">
              <div class="flex flex-row justify-between items-center">
                <div class="flex flex-row items-center w-[80%]">
                  <input
                    type="text"
                    class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
                    placeholder="Text..."
                  />
                </div>
              </div>
            </div>
            <!-- file -->
            <div v-if="question.type === 'file'">
              <div class="flex flex-row justify-between items-center">
                <div class="flex flex-row items-center w-[80%]">
                  <input
                    type="file"
                    class="w-full px-3 py-2 border-b-2 focus:outline-none focus:border-b-2 focus:border-b-pink-700 focus:border-opacity-45"
                    placeholder="Text..."
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- delete question -->
        <div class="w-[10%] flex flex-row items-center justify-around">
          <button
            v-on:click="deleteQuestion(question.id)"
            class=""
            type="button"
          >
            <i class="fa-regular fa-trash-can"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Options from "./Options.vue";

export default {
  components: { Options },
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

      question.type = type;
    },
    changeOtherType(question, type) {
      question.options = [];

      question.type = type;
    },

    addMoreOption(question) {
      if (!question.options) {
        question.options = [];
      }

      question.options.push({
        questionId: "",
        optionContent: "",
        isChecked: false,
      });
      console.log("Added question:", question.options);
    },

    async deleteQuestion(questionId) {
      this.questions.splice(questionId, 1);
    },
  },
};
</script>

<style scoped></style>
