<template>
    <div>
        <h1 class="text-3xl md:text-2xl font-bold mb-3 text-left">Nyligste aktivitet</h1>
        <hr style="height:1px;border-width:0;color:gray;background-color:gray"> 
        <main class="text-on-same-line">
          <p class="text-left italic" >{{date}} </p>
          <p class="text-right"> kr {{amount}} </p>
        </main>
        <main class="text-on-same-line">
          <p class="text-left" > Fra: {{from}} </p>
          <p class="text-right" > Til: {{to}} </p>
        </main>
    </div>
</template>

<script>
export default {
    name: "RecentTransaction",
    props: ["date", "from", "to", "amount"]
}
</script>

<style>

</style>
