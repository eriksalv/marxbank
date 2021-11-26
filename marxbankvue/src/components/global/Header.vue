<template>
  <header class="w-screen text-left bg-gray-600 text-white p-4 mb-10 shadow-lg">
    <div class="w-screen flex flex-row flex-wrap justify-center">
      <img
        class="mr-6 float-left w-12 h-12 md:w-24 md:h-24"
        src="../../assets/logo.png"
        alt="logo" />
      <h1 class="relative top-2 md:top-4 text-3xl md:text-6xl font-bold mb-3">
        Marx Bank
      </h1>
    </div>
  </header>
</template>

<script>
export default {
  name: "Header",
};
</script>

<style scoped>
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
</style>
