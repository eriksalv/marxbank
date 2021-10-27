<template>
  <div class="p-0 m-0 bg-gray-200 h-screen w-screen">
    <div v-if="this.loggedIn">
    <!-- <Header/>
    <SideBar/> -->
    </div>
    <router-view></router-view>
    <main class="flex flex-row flex-wrap justify-center items-start ml-16 bg-gray-200">
    </main>
  </div>
</template>

<script>
// import Header from "@/components/Header.vue"
// import SideBar from "@/components/SideBar.vue"

export default {
  
  // data: () => {
  //   loggedIn: false
  // },
  components: {
    // Header,
    // SideBar
  }
}
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