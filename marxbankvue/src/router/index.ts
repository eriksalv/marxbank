import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Home from "@/views/Home.vue";
import store from "@/store";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    component: Home,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/createTransaction",
    name: "CreateTransaction",
    component: () => import("@/views/CreateTransaction.vue"),
    meta: {
      requiresAuth: true,
    },
  },

  {
    path: "/myProfile",
    name: "MyProfile",
    // route level code-splitting
    // this generates a separate chunk (myProfile.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "myProfile" */ "@/views/MyProfile.vue"),
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/myTransactions",
    name: "MyTransactions",

    component: () => import("@/views/MyTransactions.vue"),
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/myAccounts",
    name: "MyAccounts",

    component: () => import("@/views/MyAccounts.vue"),
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/calculator",
    name: "Calculator",
    component: () => import("@/views/Calculator.vue"),
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/Login.vue"),
  },
  {
    path: "/myAccounts/:id",
    name: "AccountInfo",
    component: () => import("@/views/AccountInfo.vue"),
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

/**
 * Denies access to routes with requiresAuth set to true, when
 * user is not logged in
 */
router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (store.getters.isLoggedIn) {
      next();
      return;
    }
    next("/login");
  } else {
    next();
  }
});

export default router;
