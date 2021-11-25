<template>
  <div
    class="
      p-0
      m-0
      bg-gray-200
      h-screen
      w-screen
      overflow-x-hidden
      flex flex-wrap
      justify-center
    ">
    <Loading
      class="mt-20"
      :loading="
        accountStatus === 'loading' ||
        authStatus === 'loading' ||
        transactionStatus === 'loading' ||
        userStatus === 'loading'
      " />
    <div v-if="isLoggedIn">
      <Header />
      <SideBar />
    </div>
    <main
      class="
        flex flex-row flex-wrap
        justify-center
        items-start
        relative
        bg-gray-200
        w-screen
        min-h-screen
      ">
      <router-view />
    </main>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import SideBar from "@/components/SideBar.vue";
import Loading from "./components/global/Loading.vue";
import { mapGetters, mapActions } from "vuex";

export default {
  components: {
    Header,
    SideBar,
    Loading,
  },
  computed: {
    ...mapGetters([
      "getToken",
      "isLoggedIn",
      "authStatus",
      "transactionStatus",
      "userStatus",
      "accountStatus",
    ]),
  },
  created() {
    //localStorage.removeItem("tokenData");
    this.autoLogin();
  },
  mounted() {
    //if user is not logged in, send to login page
    if (!this.isLoggedIn) {
      this.$router.push({ path: "/login" });
    }
  },
  methods: {
    ...mapActions(["autoLogin"]),
  },
};
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
