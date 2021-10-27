<template>
  <tr class="bg-gray-50 dark:bg-gray-800 hover:bg-gray-100 dark:hover:bg-gray-900 text-gray-700 dark:text-gray-400 cursor-pointer">
    <td class="px-4 py-3 font-bold"> {{date}} </td>
    <td class="px-4 py-3 text-sm"> {{from}}</td>
    <td class="px-4 py-3 text-sm"> {{to}}</td>
    <td class="px-4 py-3 text-xs">
      <span class="px-2 py-1 font-semibold leading-tight text-green-100 bg-gray-700 rounded-full dark:bg-green-700 dark:text-green-100"> {{amount}} kr </span>
    </td>
  </tr>
</template>

<script>
export default {
    name: "Transaction",
    props: ["date", "from", "to", "amount"]
}
</script>

<style>

</style>