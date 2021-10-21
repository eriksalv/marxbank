<template>
  <div class="home">
    <Accounts></Accounts>
  </div>
</template>

<script>
// @ is an alias to /src
import Accounts from '../components/Accounts.vue'

export default {
  name: 'Home',
  components: {
    Accounts
  }
}
</script>
