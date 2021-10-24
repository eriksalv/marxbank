<template>
  <!--<input type="text" @input="onInput" placeholder="Søk etter navn på konto..." 
        class="box-border border-none focus:border-b w-full py-4 px-6 text-gray-700 leading-tight">-->
    <div className="search">
        <div className="searchInputs">
            <input v-model="searchTerm" type="text" placeholder="Søk..." className="input"/>
        </div>
        <div className="dataResult">
            <div v-for="account in filterAccountsByName(searchTerm)" :key="account.id"
                class="">{{ account.name }}</div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: "SearchBar",
    computed: mapGetters(["filterAccountsByName"]),
    props: ['data', 'placeholder'],
    data() {
        return {
            searchTerm: ""
        }
    }
}
</script>

<style>

</style>