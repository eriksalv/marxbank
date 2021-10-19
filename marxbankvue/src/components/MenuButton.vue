<template>
    <button @click="onClick()" class="btn" :style="{background: color}">Add Task</button>
</template>

<script>
export default {
    name: 'MenuButton',
    props: {
        text: String,
        color: String
    },
    methods: {
        onClick() {
            console.log("click")
        },
    }
}
</script>