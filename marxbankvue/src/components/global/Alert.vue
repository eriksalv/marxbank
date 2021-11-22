<template>
  <div class="rounded-md bg-green-50 p-4">
    <div class="flex">
      <div class="flex-shrink-0">
        <fa :icon="'check'" />
      </div>
      <div class="ml-3">
        <p class="text-sm font-medium text-green-800">
          {{ message }}
        </p>
      </div>
      <div class="ml-auto pl-3">
        <div class="-mx-1.5 -my-1.5">
          <button
            @click="hideAlert"
            type="button"
            class="
              inline-flex
              bg-green-50
              rounded-md
              p-1.5
              text-green-500
              hover:bg-green-100
              focus:outline-none
              focus:ring-2
              focus:ring-offset-2
              focus:ring-offset-green-50
              focus:ring-green-600
            ">
            <span class="sr-only">Dismiss</span>
            <fa :icon="'times'" />
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["message"],
  methods: {
    hideAlert() {
      this.$emit("onHideAlert");
    },
  },
};
</script>

<style></style>
