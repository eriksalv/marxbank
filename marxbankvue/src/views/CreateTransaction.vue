<template>
<main>
  <SearchBar @termChanged="onTermChanged"/>
  <p v-for="account in filterAccounts(searchTerm)" :key="account.id">{{ account.name }}</p>
</main>
</template>

<script>
import SearchBar from '@/components/createTransaction/SearchBar.vue'
import { mapGetters } from 'vuex';

export default {
    name: 'CreateTransaction',
    computed: mapGetters(["filterAccounts"]),
    components: {
        SearchBar,
    },
    methods: {
        onTermChanged(searchTerm) {
            this.searchTerm = searchTerm
            console.log(this.searchTerm)
        }
    },
    data() {
        return {
            searchTerm: String
        }
    }
}
</script>

<style>

</style>