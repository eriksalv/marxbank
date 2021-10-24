<template>
    <div className="searchInputs">
        <input @input="onInput" ref="inputField" type="text" :placeholder="placeholder" className="input"/>
    </div>
</template>

<script>

export default {
    name: "SearchBar",
    methods: {
        onInput(event) {
            this.$emit("termChanged", event.target.value);
        },
        clearInput() {
            this.$refs.inputField.value = "";
        }
    },
    props: {
        placeholder: String
    }
}
</script>

<style>

</style>