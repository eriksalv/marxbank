<template>
  <header>
      <h1 className="text-center font-semibold text-red-600">Mine kontoer</h1>
      <Account :balance="account.balance" :accName="account.name" v-for="account in allAccounts" :key="account.id" class="account" />
  </header>
</template>

<script>
import Account from './Account.vue';
import { mapGetters } from 'vuex';

export default {
    name: 'AccountList',
    computed: mapGetters(['allAccounts']),
    components: {
      Account
    }
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