<template>
  <SearchBar @termChanged="onTermChanged"/>
</template>

<script>
import SearchBar from '@/components/createTransaction/SearchBar.vue'

export default {
    name: 'CreateTransaction',
    components: {
        SearchBar
    },
    methods: {
        onTermChanged(searchTerm) {
            console.log(searchTerm)
        }
    }
}
</script>

<style>

</style>