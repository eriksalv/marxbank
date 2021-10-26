<template>
  <tr class="bg-gray-50 dark:bg-gray-800 hover:bg-gray-100 dark:hover:bg-gray-900 text-gray-700 dark:text-gray-400 cursor-pointer">
    <td class="px-4 py-3 font-bold"> {{accName}} </td>
    <td class="px-4 py-3 text-xs">
      <span class="px-2 py-1 font-semibold leading-tight text-green-100 bg-green-500 rounded-full dark:bg-green-700 dark:text-green-100"> {{balance}} kr </span>
    </td>
    <td class="px-4 py-3 text-sm"> {{type}}</td>
    <td class="px-4 py-3 text-sm"> {{accNumber}} </td>
  </tr>
</template>

<script>
export default {
    name: "Account",
    props: ["accName", "balance", "accNumber", "type"]
}
</script>

<style>

</style>