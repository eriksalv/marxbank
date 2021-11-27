<template>
  <tr
    class="
      bg-gray-50
      dark:bg-gray-800
      hover:bg-gray-100
      dark:hover:bg-gray-900
      text-gray-700
      dark:text-gray-400
      cursor-pointer
    "
    @click="$emit('accountSelected', id)">
    <td class="px-4 py-3 font-bold">
      {{ accName }}
    </td>
    <td class="px-4 py-3 text-xs">
      <span
        class="
          px-2
          py-1
          font-semibold
          leading-tight
          text-green-100
          bg-green-500
          rounded-full
          dark:bg-green-700 dark:text-green-100
        ">
        {{ balance }} kr
      </span>
    </td>
    <td class="px-4 py-3 text-sm">
      {{ type }}
    </td>
    <td class="px-4 py-3 text-sm">
      {{ id }}
    </td>
  </tr>
</template>

<script>
export default {
  name: "Account",
  props: {
    accName: {
      type: String,
      default: "",
    },
    balance: {
      type: Number,
      default: 0,
    },
    accNumber: {
      type: Number,
      default: 0,
    },
    type: {
      type: String,
      default: "",
    },
    id: {
      type: Number,
      default: 0,
    },
  },
  emits: ["accountSelected"],
};
</script>

<style></style>
