import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import ProjectList from "@/components/ProjectList.vue";
import ProjectDetails from "@/components/ProjectDetails.vue";
import FormDetails from "@/components/FormDetails.vue";
import ProjectCreate from "@/components/ProjectCreate.vue";
import FormCreate from "@/components/FormCreate.vue";
<<<<<<< HEAD
=======
import FormEdit from "@/components/FormEdit.vue";
//THANH
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
// import Auth from "@/views/Auth.vue";
import AuthForm from "@/components/AuthForm.vue";
>>>>>>> main

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
<<<<<<< HEAD
=======
    {
      path: "/projects/forms/edit",
      name: "form-edit",
      component: FormEdit,
    },
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
    
>>>>>>> main
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
