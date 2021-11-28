<template>
  <div class="layout">
    <main class="text-on-same-line text-black">
      <div>
        <p class="text-left font-medium">
          {{ accName }}
        </p>
        <p class="text-left text-xs">
          {{ id }}
        </p>
      </div>
      <p class="text-left mt-2 font-medium">kr {{ balance }}</p>
    </main>
  </div>
</template>

<script>
export default {
  name: "MyFavorite",
  props: {
    accName: {
      type: String,
      default: "",
    },
    balance: {
      type: Number,
      default: 0,
    },
    id: {
      type: Number,
      default: 0,
    },
  },
};
</script>

<style scoped>
.layout {
  background: #b9b7b7;
  margin: 6px;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 4px;
}
.text-on-same-line {
  display: flex;
  justify-content: space-between;
}
</style>
