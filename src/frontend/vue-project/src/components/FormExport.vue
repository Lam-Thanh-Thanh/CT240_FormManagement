<template>
  <!-- dropdown -->
  <div class="flex gap-16 items-start mx-72 my-32 mb-20 justify-between">
    <h1 class="text-4xl font-extrabold">Form edit</h1>
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
    <div class="text-left mx-32 my-20">
      <div class="pb-3">
        <span class="font-bold pr-2">Title:</span>
        <span class=""> {{ form.title }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Description:</span>
        <span class="">{{ form.description }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Project Name:</span>
        <span class=""> ..</span>
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

    <!-- question -->
    <div
      v-for="(question, index) in form.questions"
      :key="index"
      class="mx-32 my-20 shadow-lg shadow-myLightGray p-6 rounded border-t-pink-800 border-t-4"
    >
      <!-- content -->
      <div class="flex row justify-between items-start">
        <div class="w-[80%]">
          <p>{{ question.content }}</p>
        </div>

        <!-- dropdown -->
      </div>
      <!-- option -->
      <div class="mt-7">
        <div class="flex flex-col">
          <div
            v-for="(option, index) in question.options"
            :key="index"
            class="flex flex-row items-center"
          >
            <!-- question.type -->
            <input type="radio" name="radioOption" v-model="option.isChecked" />

            <p>{{ option.optionContent }}</p>
          </div>

          <!-- more option button -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FormService from "@/services/FormService";
export default {
  props: ["formId"],
  data() {
    return {
      open: false,
      // oldTitle: "form1",
      form: {
        questions: [],
      },
    };
  },
  async created() {
    await this.getFormDetails();
  },
  methods: {
    async getFormDetails() {
      try {
        const response = await FormService.getFormDetails(this.formId);
        this.form = response.data;
        console.log(response.data);
      } catch (error) {
        console.error("There was an error getting form details:", error);
      }
    },

    // editForm() {
    //   try {
    //     this.$router.push({
    //       name: "form-edit",
    //       query: { oldTitle: this.oldTitle },
    //     });
    //   } catch (error) {}
    // },
  },
};
</script>

<style lang="scss" scoped></style>
