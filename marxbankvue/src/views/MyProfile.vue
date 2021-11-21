<template>
  <div class="w-2/3">
    <transition name="fade">
      <Alert @onHideAlert="resetMsg" v-if="!errorMsg && successMsg" :message="successMsg" />
    </transition>
    <transition name="fade">
      <ErrorAlert @onHideAlert="resetMsg" v-if="errorMsg && !successMsg" :message="errorMsg" />
    </transition>
    <h1 class="title">
      {{ getLoggedInUser.username }}
    </h1>
    <label for="username">Nytt Brukernavn</label>
    <input
      id="username"
      v-model="newUsername"
      type="text"
      placeholder="Nytt brukernavn"
      class="input" />
    <label for="email">Ny Epost</label>
    <input
      id="email"
      v-model="newEmail"
      type="text"
      placeholder="Ny epost"
      class="input" />
    <label for="password">Nytt Passord</label>
    <input
      id="password"
      v-model="newPassword"
      type="password"
      placeholder="Nytt passord"
      class="input" />
    <label for="currentPassword">Nåværende Passord</label>
    <input
      id="currentPassword"
      v-model="currentPassword"
      type="password"
      placeholder="Nåværende passord"
      class="input" />
    <button class="button" @click="editProfile">Lagre endringer</button>
    <button class="button bg-red-600" @click="requestLogout">Logg ut</button>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import Alert from '@/components/global/Alert.vue';
import ErrorAlert from '@/components/global/ErrorAlert.vue';

export default {
  name: "MyProfile",
  components: {
    Alert,
    ErrorAlert,
  },
  data() {
    return {
      newUsername: null,
      newPassword: null,
      currentPassword: null,
      newEmail: null,
      errorMsg: null,
      successMsg: null,
    };
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
      const username = this.newUsername
        ? this.newUsername
        : this.getLoggedInUser.username;
      const password = this.newPassword
        ? this.newPassword
        : this.currentPassword;
      const email = this.newEmail ? this.newEmail : this.getLoggedInUser.email;
      const request = {
        username,
        password,
        oldPassword: this.currentPassword,
        email,
      };
      await this.editUser({ id: this.getUserId, request })
        .then(() => {
          this.errorMsg = false;
          this.successMsg = "Changes saved successfully";
        })
        .catch((err) => {
          this.successMsg = false;
          this.errorMsg = err.message;
        });
    },
    resetMsg() {
      this.errorMsg = null;
      this.successMsg = null;
    }
  },
};
</script>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.4s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
