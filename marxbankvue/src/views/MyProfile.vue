<template>
  <div class="w-2/3">
    <h1 class="title">
      {{ getLoggedInUser.username }}
    </h1>
    <label for="username">Nytt Brukernavn</label>
    <input
      v-model="newUsername"
      id="username"
      type="text"
      placeholder="Nytt brukernavn"
      class="input" />
    <label for="email">Ny Epost</label>
    <input
      v-model="newEmail"
      id="email"
      type="text"
      placeholder="Ny epost"
      class="input" />
    <label for="password">Nytt Passord</label>
    <input
      v-model="newPassword"
      id="password"
      type="password"
      placeholder="Nytt passord"
      class="input" />
    <label for="currentPassword">Nåværende Passord</label>
    <input
      v-model="currentPassword"
      id="currentPassword"
      type="password"
      placeholder="Nåværende passord"
      class="input" />
    <button class="button" @click="editProfile">Lagre endringer</button>
    <button class="button bg-red-600" @click="requestLogout">Logg ut</button>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: "MyProfile",
  data() {
    return {
      newUsername: null,
      newPassword: null,
      currentPassword: null,
      newEmail: null,
    }
  },
  computed: {
    ...mapGetters(["getLoggedInUser", "getToken", "getUserId"]),
  },
  async created() {
    await this.fetchUserById(this.getUserId);
  },
  methods: {
    ...mapActions(["logout", "fetchUserById", "editUser"]),
    requestLogout() {
      this.logout(this.getToken).then(() => {
        this.$router.push("/login");
      });
    },
    async editProfile() {
      const username = this.newUsername ? this.newUsername : this.getLoggedInUser.username;
      const password = this.newPassword ? this.newPassword : this.currentPassword;
      const email = this.newEmail ? this.newEmail : this.getLoggedInUser.email;
      const request = {
        username,
        password,
        oldPassword: this.currentPassword,
        email,
      };
      await this.editUser({ id: this.getUserId, request }).catch((err) => {
        console.log(err);
      });
    }
  },
};
</script>
