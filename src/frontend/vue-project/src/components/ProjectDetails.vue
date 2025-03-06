<template>
  <!-- dropdown -->
  <div class="flex gap-16 items-start mx-60 my-32 justify-between">
    <h1 class="text-4xl font-extrabold">Project Details</h1>
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
          <button class="">Edit Infomation</button>
        </div>
        <div class="hover:bg-red-400 px-2 py-1">
          <button class="">Delete</button>
        </div>
      </div>
    </div>
  </div>

  <div class="mt-0 m-40">
    <!-- project information -->
    <div class="text-left mx-20 my-32">
      <div class="pb-3">
        <span class="font-bold pr-2">Name:</span>
        <span class="">{{ project.name }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Description:</span>
        <span class="">{{ project.description }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Date created:</span>
        <span class="">Date</span>
      </div>
    </div>

    <!-- form list -->
    <div class="flex flex-wrap gap-14 justify-start mx-20 mt-32 mb-48">
      <div
        class="w-[22%] bg-white shadow-lg hover:shadow-md rounded-2xl transition duration-300 ease-in-out"
        v-for="form in project.forms"
        :key="form.id"
      >
        <button v-on:click="viewFormDetails(form.id)" class="w-full text-left">
          <div
            class="font-bold text-lg bg-gray-200 border-2 border-myLightNavy rounded-t-2xl text-center py-2 px-4 overflow-hidden whitespace-nowrap overflow-ellipsis"
          >
            {{ form.title }}
          </div>
          <div class="py-3 px-4">
            <p class="overflow-hidden whitespace-nowrap overflow-ellipsis">
              {{ form.description }}
            </p>
            <p class="">Date:</p>
          </div>
          <!-- delete form -->
        </button>

        <button
          type="button"
          v-on:click="deleteForm(form.id)"
          class="float-right pb-3 px-6"
        >
          <i
            class="fa-regular fa-trash-can hover:bg-gray-200 p-2 rounded-full text-gray-400 hover:text-gray-700"
          ></i>
        </button>
      </div>
      <!-- create new -->

      <button
        v-on:click="addForm"
        class="w-[15%] px-6 py-4 bg-white shadow-md hover:shadow-lg rounded-2xl"
      >
        <p class="text-xl mb-2">Add form</p>
        <i class="fa-solid fa-plus"></i>
      </button>
    </div>
    <!-- edit delete -->
  </div>
</template>

<script>
import ProjectService from "@/services/ProjectService";
import FormService from "@/services/FormService";
export default {
  props: ["projectId"],
  data() {
    return {
      open: false,
      project: "",
      forms: [],
    };
  },
  async created() {
    await this.getProjectDetails();
  },
  methods: {
    async getProjectDetails() {
      try {
        const response = await ProjectService.getProjectDetials(this.projectId);
        this.project = response.data;
        //
        // const response1 = await FormService.getAllFormOfProject(this.projectId);   //id
        // this.form = response.data;

        console.log(response.data);
      } catch (error) {
        console.error("There was an error getting project details:", error);
      }
    },
    viewFormDetails(seletedFormId) {
      try {
        this.$router.push({
          name: "form-edit",
          params: { formId: seletedFormId, projectId: this.projectId },
        });
      } catch (error) {}
    },
    addForm() {
      try {
        this.$router.push({
          name: "form-create",
          params: { projectId: this.projectId },
        });
      } catch (error) {}
    },
    async deleteForm(formId) {
      const response = await FormService.deleteFormOfProject(
        this.projectId,
        formId
      );
      this.project.forms.splice(formId, 1);

      console.log(response.data);
    },
  },
};
</script>

<style lang="scss" scoped></style>
