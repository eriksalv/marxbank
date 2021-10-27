import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Home from "@/views/Home.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/createTransaction",
    name: "CreateTransaction",
    component: () => import("@/views/CreateTransaction.vue"),
  },
    
  {
    path: '/myProfile',
    name: 'MyProfile',
    // route level code-splitting
    // this generates a separate chunk (myProfile.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "myProfile" */ '@/views/MyProfile.vue')
    },
    {
    path: '/transactions',
    name: 'Transactions',
    
    component: () => import('@/views/Transactions.vue')
    },
    {
      path: '/myAccounts',
      name: 'MyAccounts',
      
      component: () => import('@/views/MyAccounts.vue')
    },
    {
      path: '/calculator',
      name: 'Calculator',
      
      component: () => import('@/views/Calculator.vue')
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue')
    }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
