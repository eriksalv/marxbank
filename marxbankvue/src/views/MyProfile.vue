<template>
  <div class="w-2/3">
    <h1 class="title">{{ getUserData() }}</h1>
    <label for="username">Brukernavn</label>
    <input type="text" id="username" placeholder="Nytt brukernavn" class="input">
    <label for="password">Passord</label>
    <input type="password" id="password" placeholder="Nytt passord" class="input">
    <button class="button">Lagre endringer</button>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: 'MyProfile',
    computed: {
      ...mapGetters(['currentUser'])
    },
    methods: {
      getUserData() {
        //TODO: fetch bruker fra api
        return this.$store.getters.currentUser("69").id;
      },
    }
}
</script>
