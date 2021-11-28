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
    <td class="px-4 py-3 font-bold">
      {{ date }}
    </td>
    <td class="px-4 py-3 text-sm">{{ fromAccount.name }} : {{ from }}</td>
    <td class="px-4 py-3 text-sm">{{ toAccount.name }} : {{ to }}</td>
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
        kr {{ amount }}
      </span>
    </td>
  </tr>
</template>

<script>
export default {
  name: "Transaction",
  props: {
    date: {
      type: String,
      default: "",
    },
    from: {
      type: Number,
      default: 0,
    },
    to: {
      type: Number,
      default: 0,
    },
    amount: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      fromAccount: null,
      toAccount: null,
    };
  },
  created() {
    if (this.from && this.to) {
      this.fromAccount = this.$store.getters.getAccountById(
        parseInt(this.from)
      );
      this.toAccount = this.$store.getters.getAccountById(parseInt(this.to));
    }
  },
};
</script>

<style></style>
