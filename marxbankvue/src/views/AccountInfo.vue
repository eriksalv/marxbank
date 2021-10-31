<template>
  <main class="flex pb-8">
    <div class="flex items-center">
        <h1 class="ml-3 text-2xl font-bold leading-7 text-gray-600 sm:leading-9 sm:truncate">
        {{selectedAccount.name}}
        </h1>
    </div>
</main>
</template>

<script lang="ts">
import {mapGetters} from 'vuex';

export default {
    created() {
        //TODO: fetch kontoer fra api
        this.selectedAccount = this.$store.getters.getAccountById(parseInt(this.id))
        console.log(this.selectedAccount);
    },
    props: ["id"],
    computed: {
        ...mapGetters['getAccountById']
    },
    data() {
        return {
            selectedAccount: Object
        }
    }
}
</script>

<style>

</style>