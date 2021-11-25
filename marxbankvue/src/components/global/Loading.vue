<template>
  <img
    src="/Sickle.svg"
    alt=""
    :class="
    status === 'loading'
        ? 'communismIcon left-1 loading'
        : 'communismIcon left-1'
    " />
</template>

<script>
export default {
    props: ["status"],
}
</script>

<style>
.communismIcon {
  @apply w-12 absolute top-3 z-0 origin-center;
}

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