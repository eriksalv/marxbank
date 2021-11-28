<template>
  <transition name="fade">
    <div v-if="message" class="rounded-md bg-red-50 p-4">
      <div class="flex">
        <div class="flex-shrink-0">
          <fa :icon="'exclamation-triangle'" />
        </div>
        <div class="ml-3">
          <p class="text-sm font-medium text-red-800">
            {{ message }}
          </p>
        </div>
        <div class="ml-auto pl-3">
          <div class="-mx-1.5 -my-1.5">
            <button
              type="button"
              class="
                inline-flex
                bg-red-50
                rounded-md
                p-1.5
                text-red-500
                hover:bg-red-100
                focus:outline-none
                focus:ring-2
                focus:ring-offset-2
                focus:ring-offset-red-50
                focus:ring-red-600
              "
              @click="hideAlert">
              <span class="sr-only">Dismiss</span>
              <fa :icon="'times'" />
            </button>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  props: {
    message: {
      type: String,
      default: "",
    },
  },
  emits: ["onHideAlert"],
  methods: {
    hideAlert() {
      this.$emit("onHideAlert");
    },
  },
};
</script>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.4s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
