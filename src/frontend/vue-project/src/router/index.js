import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import ProjectList from "@/components/ProjectList.vue";

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
  ],
});

export default router;
