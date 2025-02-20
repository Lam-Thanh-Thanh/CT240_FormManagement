import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import ProjectList from "@/components/ProjectList.vue";
import ProjectDetails from "@/components/ProjectDetails.vue";
import FormDetails from "@/components/FormDetails.vue";
import ProjectCreate from "@/components/ProjectCreate.vue";
import FormCreate from "@/components/FormCreate.vue";
import FormEdit from "@/components/FormEdit.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: Home,
    },
    {
      path: "/projects",
      name: "project-list",
      component: ProjectList,
    },
    {
      path: "/projects/project-details",
      name: "project-details",
      component: ProjectDetails,
    },
    {
      path: "/projects/form-details",
      name: "form-details",
      component: FormDetails,
    },
    {
      path: "/projects/create",
      name: "project-create",
      component: ProjectCreate,
    },
    {
      path: "/projects/forms/create",
      name: "form-create",
      component: FormCreate,
    },
    {
      path: "/projects/forms/edit",
      name: "form-edit",
      component: FormEdit,
    },
  ],
});

export default router;
