<template>
  <router-link :to="to" class="sidebar-icon group">
    <fa :icon="icon" />

    <span className="sidebar-tooltip group-hover:scale-100">
      {{ text }}
    </span>
  </router-link>
</template>

<script>
export default {
  name: "SideBarIcon",
  props: {
    icon: {
      type: String,
      default: "",
    },
    text: {
      type: String,
      default: "",
    },
    to: {
      type: String,
      default: "",
    },
  },
};
</script>

<style>
a.router-link-exact-active {
  --tw-bg-opacity: 1;
  background-color: rgba(5, 150, 105, var(--tw-bg-opacity));
  border-radius: 0.75rem;
  color: white;
}
</style>
