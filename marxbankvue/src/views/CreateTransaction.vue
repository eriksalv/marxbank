<template>
<main class="w-1/4">
    <div id="reciever" class="relative w-full rounded-sm inline-block">
        <h1>Velg en konto</h1>
        <SearchBar/>
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
    data() {
        return {
            showDropdown: false
        }
    }
}
</script>

<style>

</style>