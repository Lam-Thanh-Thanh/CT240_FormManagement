import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import ProjectList from "@/components/ProjectList.vue";
import ProjectDetails from "@/components/ProjectDetails.vue";

import ProjectCreate from "@/components/ProjectCreate.vue";
import FormCreate from "@/components/FormCreate.vue";
import FormEdit from "@/components/FormEdit.vue";
<<<<<<< HEAD
//THANH
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
// import Auth from "@/views/Auth.vue";
import AuthForm from "@/components/AuthForm.vue";
=======
import FormExport from "@/components/FormExport.vue";

//THANH
import Login from "@/components/Login.vue";
import Register from "@/components/Register.vue";
>>>>>>> origin/van

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
<<<<<<< HEAD
    // THANH
    { path: "/auth",
      component: AuthForm,
    },
    {
      path: "/login",
      name: "login",
      component: Login,
    },
    {
      path: "/register",
      name: "register",
      component: Register,
    },
    
=======
    /*----------------------------*/
    {
          path: "/login",
          name: "login",
          component: Login,
        },
        {
          path: "/register",
          name: "register",
          component: Register,
        },
>>>>>>> origin/van
  ],
});

//THANH
// Kiểm tra xem user đã đăng nhập chưa
router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem("token") !== null;
  if (to.meta.requiresAuth && !isAuthenticated) {
    next("/login");
  } else {
    next();
  }
});

export default router;
