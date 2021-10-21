<template>
  <div id="accountContainer" className="flex">
    <h1 className="text-left">{{ accName }}</h1>
    <p>{{ balance }}</p>
  </div>
</template>

<script>
export default {
    name: "Account",
    props: ["accName", "balance"]
}
</script>

<style>

</style>