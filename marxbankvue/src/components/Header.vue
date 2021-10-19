<template>
    <header>
        <h1>{{title}}</h1>
        <MenuButton text="Nice" color="green"></MenuButton>
    </header>
</template>

<script>
import MenuButton from './MenuButton'

export default {
    name:"header",
    props: {
        title: {
            type: String,
            default: "Hello World"
        }
    },
    components: {
        MenuButton,
    }
}
</script>

<style scoped>
header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom:20px;
}
</style>