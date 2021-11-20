<template>
  <div class="w-2/3">
    <h1 class="title">
      {{ getLoggedInUser.username }}
    </h1>
    <label for="username">Brukernavn</label>
    <input
      id="username"
      type="text"
      placeholder="Nytt brukernavn"
      class="input" />
    <label for="password">Passord</label>
    <input
      id="password"
      type="password"
      placeholder="Nytt passord"
      class="input" />
    <button class="button">Lagre endringer</button>
    <button class="button bg-red-600" @click="requestLogout">Logg ut</button>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: "MyProfile",
  computed: {
    ...mapGetters(["getLoggedInUser", "getToken", "getUserId"]),
  },
  async created() {
    await this.fetchUserById(this.getUserId);
  },
  methods: {
    ...mapActions(["logout", "fetchUserById"]),
    requestLogout() {
      this.logout(this.getToken).then(() => {
        this.$router.push("/login");
      });
    },
  },
};
</script>
