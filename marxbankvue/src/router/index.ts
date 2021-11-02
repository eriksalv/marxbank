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
<<<<<<< HEAD
    meta: {
      requiresAuth: true,
    },
=======
>>>>>>> origin/master
  },
  {
    path: "/myTransactions",
    name: "MyTransactions",

    component: () => import("@/views/MyTransactions.vue"),
<<<<<<< HEAD
    meta: {
      requiresAuth: true,
    },
=======
>>>>>>> origin/master
  },
  {
    path: "/myAccounts",
    name: "MyAccounts",

    component: () => import("@/views/MyAccounts.vue"),
<<<<<<< HEAD
    meta: {
      requiresAuth: true,
    },
=======
>>>>>>> origin/master
  },
  {
    path: "/calculator",
    name: "Calculator",
<<<<<<< HEAD
=======

>>>>>>> origin/master
    component: () => import("@/views/Calculator.vue"),
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/Login.vue"),
  },
<<<<<<< HEAD
=======
  {
    path: "/myAccounts/:id",
    name: "AccountInfo",
    component: () => import("@/views/AccountInfo.vue"),
    props: true,
  },
>>>>>>> origin/master
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
