<template>
  <input type="text" @input="onInput">
</template>

<script>
export default {
    name: "SearchBar",
    methods: {
        onInput(event) {
            this.$emit('termChanged', event.target.value)
        } 
    }
}
</script>

<style>

</style>