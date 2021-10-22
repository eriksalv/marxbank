<template>
  <router-link :to="to" class="sidebar-icon group">
      {{ icon }}

      <span className="sidebar-tooltip group-hover:scale-100">
          {{ text }}
      </span>
  </router-link>
</template>

<script>
//import { useRoute } from "vue-router";

export default {
    name: "SideBarIcon",
    props: ["icon", "text", "to"],
    /*setup(props) {
        const route = useRoute()
        const isActive = route.path === props.to
        return isActive
    }*/
}
</script>

<style>

</style>