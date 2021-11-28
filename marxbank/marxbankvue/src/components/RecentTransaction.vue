<template>
  <div>
    <main class="text-on-same-line">
      <p class="text-left italic">
        {{ date }}
      </p>
      <p class="text-right">kr {{ amount }}</p>
    </main>
    <main class="text-on-same-line">
      <p class="text-left">Fra: {{ from }}</p>
      <p class="text-right">Til: {{ to }}</p>
    </main>
  </div>
</template>

<script>
export default {
  name: "RecentTransaction",
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
};
</script>

<style></style>
