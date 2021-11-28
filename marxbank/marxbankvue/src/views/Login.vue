<template>
  <div class="flex w-full h-full mt-3">
    <LoginComponent />
  </div>
</template>
<script lang="ts">
import LoginComponent from "../components/LoginComponent.vue";

export default {
  name: "Login",
  components: {
    LoginComponent,
  },
};
</script>
