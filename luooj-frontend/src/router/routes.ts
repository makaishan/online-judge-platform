import { RouteRecordRaw } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import AboutView from "@/views/AboutView.vue";
import AdminView from "@/views/AdminView.vue";
import NoAuthView from "@/views/NoAuthView.vue";

export const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: "/home",
  },
  {
    path: "/home",
    name: "浏览题目",
    component: HomeView,
  },
  {
    path: "/admin",
    name: "仅管理员可见",
    component: AdminView,
    meta: {
      access: "canAdmin",
    },
  },
  {
    path: "/noAuth",
    name: "无权限",
    component: NoAuthView,
  },
  {
    path: "/about",
    name: "关于我的",
    component: AboutView,
  },
];
