<template>
  <header>
      <h2>Kontoer</h2>
      <div v-for="account in allAccounts" :key="account.id" class="account">
          {{ account.name }}
      </div>
  </header>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: 'Accounts',
    computed: mapGetters(['allAccounts'])
};
</script>

<style>
.accounts {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-gap: 1rem;
}
.account {
  border: 1px solid #ccc;
  background: #41b883;
  padding: 1rem;
  border-radius: 5px;
  text-align: center;
  position: relative;
  cursor: pointer;
}
</style>