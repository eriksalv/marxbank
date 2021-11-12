<template>
  <tr
    class="
      bg-gray-50
      dark:bg-gray-800
      hover:bg-gray-100
      dark:hover:bg-gray-900
      text-gray-700
      dark:text-gray-400
    ">
    <td class="px-4 py-3 font-bold">{{ date }}</td>
    <td class="px-4 py-3 text-sm">{{ fromAccount.name }}</td>
    <td class="px-4 py-3 text-sm">{{ toAccount.name }}</td>
    <td class="px-4 py-3 text-xs">
      <span
        class="
          px-2
          py-1
          font-semibold
          leading-tight
          text-green-100
          bg-gray-700
          rounded-full
          dark:bg-green-700 dark:text-green-100
        ">
        {{ amount }} kr
      </span>
    </td>
  </tr>
</template>

<script>
export default {
  name: "Transaction",
  props: ["date", "from", "to", "amount"],
  created() {
    this.fromAccount = this.$store.getters.getAccountById(parseInt(this.from));
    this.toAccount = this.$store.getters.getAccountById(parseInt(this.to));
  },
  data() {
    return {
      fromAccount: null,
      toAccount: null,
    };
  },
};
</script>

<style></style>
