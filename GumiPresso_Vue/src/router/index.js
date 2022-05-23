import Vue from "vue";
import VueRouter from "vue-router";
import ProductListView from "@/views/ProductListView.vue";
import RegisterView from "@/views/RegisterView.vue";
import LoginView from "@/views/LoginView.vue";
import UserInfoView from "@/views/UserInfoView.vue";
import OrderDetailView from "@/views/OrderDetailView.vue";
import ProductDetailView from "@/views/ProductDetailView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "product-list-view",
    component: ProductListView,
  },
  {
    path: "/register",
    name: "register-view",
    component: RegisterView,
  },
  {
    path: "/login",
    name: "login-view",
    component: LoginView,
  },
  {
    path: "/user-info",
    name: "user-info-view",
    component: UserInfoView,
  },
  {
    path: "/order-detail",
    name: "order-detail-view",
    component: OrderDetailView,
  },
  {
    path: "/product-detail/:id",
    name: "product-detail-view",
    component: ProductDetailView,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
