import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import ProjectList from "@/components/ProjectList.vue";
import ProjectDetails from "@/components/ProjectDetails.vue";
import ProjectEdit from "@/components/ProjectEdit.vue";
import ProjectCreate from "@/components/ProjectCreate.vue";
import FormCreate from "@/components/FormCreate.vue";
import FormEdit from "@/components/FormEdit.vue";
import FormExport from "@/components/FormExport.vue";
import FormResponses from "@/components/FormResponses.vue";

//THANH
import Login from "@/components/Login.vue";
import Register from "@/components/Register.vue";
import Account from "@/components/Account.vue";


import AdminDashboard from "@/components/AdminDashboard.vue";
import UserManagement from "@/components/UserManagement.vue";
import ProjectManagement from "@/components/ProjectManagement.vue";
import EditProfile from "@/components/EditProfile.vue";
import { AuthService } from "@/services/authService";
import {jwtDecode} from "jwt-decode";



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
      path: "/projects/:projectId/edit",
      name: "project-edit",
      component: ProjectEdit,
      props: true,
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
    {
      path: "/projects/:projectId/forms/:formId/responses",
      name: "form-responses",
      component: FormResponses,
      props: true,
    },
    {
      path: "/account",
      name: "account",
      component: Account,
    },

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
    { path: "/admin", component: AdminDashboard },
  { path: "/admin/users", component: UserManagement },
  { path: "/admin/projects", component: ProjectManagement },
  {
    path: "/edit-profile/:id",
    name: "EditProfile",
    component: EditProfile,
    meta: { requiresAuth: true },
  },
    {
      path: "/s/:shortCode",
      name: "short-url",
      beforeEnter: async (to, from, next) => {
        try {
          const response = await axios.get(
            `http://localhost:8080/api/url/${to.params.shortCode}`
          );

          if (response.data && response.data.originalUrl) {
            console.log("Chuyển hướng đến:", response.data.originalUrl);
            window.location.replace(response.data.originalUrl); // Dùng replace để tránh lưu vào lịch sử
          } else {
            console.error("Không tìm thấy URL gốc");
            next("/"); // Chuyển về trang chủ nếu không tìm thấy
          }
        } catch (error) {
          console.error("Lỗi khi lấy URL gốc:", error);
          next("/"); // Nếu lỗi, quay về trang chủ
        }
      },
    },
  ],
});
router.beforeEach((to, from, next) => {
  const token = AuthService.getToken();

  if (to.path.startsWith("/admin")) {
    if (!token) {
      alert("You need to log in with ADMIN role!");
      return next("/login");
    }

    try {
      const decodedToken = jwtDecode(token);
      if (decodedToken.role !== "ADMIN") {
        alert("You do not have access!");
        return next("/");
      }
    } catch (error) {
      console.error("Lỗi giải mã token:", error);
      return next("/login");
    }
  }

  next();
});
export default router;
