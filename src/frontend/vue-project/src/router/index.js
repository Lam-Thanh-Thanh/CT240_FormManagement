import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import ProjectList from "@/components/ProjectList.vue";
import ProjectDetails from "@/components/ProjectDetails.vue";

import ProjectCreate from "@/components/ProjectCreate.vue";
import FormCreate from "@/components/FormCreate.vue";
import FormEdit from "@/components/FormEdit.vue";
import FormExport from "@/components/FormExport.vue";

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
      path: "/projects/:projectId",
      name: "project-details",
      component: ProjectDetails,
      props: true,
    },
    {
      path: "/projects/:projectId/forms/:formId",
      name: "form-edit",
      component: FormEdit,
      props: true,
    },
    {
      path: "/projects/create",
      name: "project-create",
      component: ProjectCreate,
    },
    {
      path: "/projects/:projectId/forms/create",
      name: "form-create",
      component: FormCreate,
      props: true,
    },
    {
      path: "/:formId/",
      name: "form-export",
      component: FormExport,
      props: true,
    },
  ],
});

export default router;
