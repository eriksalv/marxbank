<template>
<main class="w-1/4">
    <div id="reciever" class="relative w-full rounded-sm inline-block">
        <h1>Velg en konto</h1>
        <SearchBar @accountSelected="onAccountSelected"/>
    </div>
</main>
</template>

<script>
import SearchBar from '@/components/createTransaction/SearchBar.vue'

export default {
    name: 'CreateTransaction',
    components: {
        SearchBar,
    },
    methods: {
        onAccountSelected(account) {
            //TODO: vis info om kontoen (kontonummer)
            console.log(account);
        }
    }
}
</script>

<style>

</style>