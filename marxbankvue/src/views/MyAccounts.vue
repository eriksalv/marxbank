<template>
  <div class="flex flex-row flex-wrap min-w-[80%]">
    <AccountList class="min-w-max"></AccountList>
  </div>
</template>

<script>
// @ is an alias to /src
import AccountList from '../components/AccountList.vue'

export default {
  name: 'MyAccounts',
  components: {
    AccountList
  }
}
</script>