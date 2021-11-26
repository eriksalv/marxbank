<template>
  <div
    v-if="loading"
    class="
      ease-linear
      transition-colors
      rounded-full
      border-8 border-t-8 border-red-600
      h-20
      w-20
      p-1
      fixed
      flex
      justify-center
      items-center
      z-50
      shadow-lg
      bg-gray-200
    ">
    <img
      src="/Sickle.svg"
      alt=""
      :class="
        loading ? 'communismIcon left-1 loading' : 'communismIcon left-1'
      " />
  </div>
</template>

<script>
export default {
  props: {
    loading: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style>
@keyframes newCommunsim {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(-360deg);
  }
}

.loading {
  animation: newCommunsim 1s linear forwards infinite;
  animation-play-state: running;
}
</style>
