<template>
    <nav class="fixed top-0 left-0 h-screen w-16 m-0 flex flex-col
                bg-gray-900 text-white shadow-lg">
        <SideBarIcon icon="A" text="Hovedmeny" to="/"/>
        <SideBarIcon icon="B" text="Overføring" to="/about"/>
        <SideBarIcon icon="C" text="Mine kontoer" to="/"/>
        <SideBarIcon icon="D" text="Sparekalkulator" to="/"/>
    </nav>
</template>

<script>
import SideBarIcon from "@/components/SideBarIcon.vue"

export default {
    name: "SideBar",
    components: {
        SideBarIcon
    }
}
</script>

<style>

</style>