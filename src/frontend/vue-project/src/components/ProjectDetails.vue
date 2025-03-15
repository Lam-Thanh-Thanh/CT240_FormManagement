<template>
  <div class="py-20 px-60 border-b">
    <div class="flex gap-16 items-start justify-between">
      <h1 class="text-4xl font-extrabold">Project Details</h1>
      <!-- dropdown -->
      <div class="text-center">
        <button
          v-on:click="open = !open"
          class="bg-zinc-200 rounded-full px-2 py-1 m-2 hover:shadow-lg"
        >
          <i class="fa-solid fa-ellipsis"></i>
        </button>
        <div class="shadow-lg rounded-md py-4 bg-white" v-if="open">
          <div class="hover:bg-yellow-300 px-10 py-1">
            <button class="" v-on:click="editProject">Edit</button>
          </div>
          <div class="hover:bg-red-500 px-10 py-1 hover:text-white">
            <button class="" v-on:click="deleteProject">Delete</button>
          </div>
        </div>
      </div>
    </div>
    <!-- project information -->
    <div class="text-left mt-9">
      <div class="pb-3">
        <span class="font-bold pr-2">Name:</span>
        <span class="">{{ project.name }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Description:</span>
        <span class="">{{ project.description }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Created at:</span>
        <span class="">{{ formattedDate(project.createdAt) }}</span>
      </div>
      <div class="pb-3">
        <span class="font-bold pr-2">Update at:</span>
        <span class="">{{ formattedDate(project.lastModifiedAt) }}</span>
      </div>
    </div>
  </div>

  <!-- form list -->
  <div class="flex flex-wrap gap-14 justify-start px-60 py-48 bg-white">
    <div
      class="w-[22%] bg-white shadow-lg hover:shadow-md rounded-2xl transition duration-300 ease-in-out"
      v-for="(form, index) in project.forms"
      :key="index"
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
        </div>
        <!-- delete form -->
      </button>

      <button
        type="button"
        v-on:click="deleteForm(index)"
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
      class="w-[15%] px-6 py-4 bg-white shadow-lg hover:shadow-md rounded-2xl transition duration-300 ease-in-out"
    >
      <p class="text-xl mb-2">Add form</p>
      <i class="fa-solid fa-plus"></i>
    </button>
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
    formattedDate(createdAt) {
      return new Date(createdAt).toLocaleString("en-US", {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit",
        timeZoneName: "short",
      });
    },
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
    async deleteForm(index) {
      const response = await FormService.deleteFormOfProject(
        this.projectId,
        this.project.forms[index].id
      );
      this.project.forms.splice(index, 1);
      alert("Form is deleted successfully!!");
      console.log(response.data);
    },
    async deleteProject() {
      const response = await ProjectService.deleteProject(this.project.id);

      console.log(response.data);
      alert("Project is deleted successfully!!");
      this.$router.push({
        name: "project-list",
      });
    },
    async editProject() {
      this.$router.push({
        name: "project-edit",
        params: { projectId: this.projectId },
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
