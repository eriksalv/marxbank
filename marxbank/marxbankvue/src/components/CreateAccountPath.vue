<template>
  <div class="layout" @click="goToMyAccounts">
    <main class="text-on-line text-black">
      <div>
        <p class="font-medium">
          <fa icon="plus" />
        </p>
      </div>
      <p class="textColor">S</p>
      <p class="font-medium">Opprett ny konto!</p>
    </main>
  </div>
</template>

<script>
export default {
  name: "CreateAccountPath",
  methods: {
    goToMyAccounts() {
      this.$router.push({ name: "MyAccounts" });
    },
  },
};
</script>

<style>
.text-on-line {
  display: flex;
  margin: 0cm;
  text-align: center;
}
.layout {
  background: #b9b7b7;
  margin: 6px;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 4px;
}
.textColor {
  color: #b9b7b7;
}
</style>
