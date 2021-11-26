<template>
  <main>
    <p class="w-2/3 text-left">{{ text }}:</p>
    <div v-if="type == 'amount'" class="amount-input-wrapper">
      <input
        type="number"
        min="0"
        class="my-input"
        @input="onInput"
        @keypress="isInteger($event)" />
      <p class="text-ending">kr</p>
    </div>
    <div v-else-if="type == 'year'" class="year-interest-input-wrapper">
      <input
        type="number"
        min="0"
        class="my-input"
        @input="onInput"
        @keypress="isInteger($event)" />
      <p class="text-ending">år</p>
    </div>
    <div v-else-if="type == 'interest'" class="year-interest-input-wrapper">
      <input
        type="number"
        min="0"
        class="my-input"
        @input="onInput"
        @keypress="isFloat($event)" />
      <p class="text-ending">%</p>
    </div>
  </main>
</template>

<script>
export default {
  props: {
    type: {
      type: String,
      default: "",
    },
    text: {
      type: String,
      default: "",
    },
  },
  emits: ["termChanged"],
  methods: {
    isFloat: function (evt) {
      evt = evt ? evt : window.event;
      var charCode = evt.which ? evt.which : evt.keyCode;
      if (
        charCode > 31 &&
        (charCode < 48 || charCode > 57) &&
        charCode !== 46
      ) {
        evt.preventDefault();
      } else {
        return true;
      }
    },
    isInteger: function (evt) {
      evt = evt ? evt : window.event;
      var charCode = evt.which ? evt.which : evt.keyCode;
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        evt.preventDefault();
      } else {
        return true;
      }
    },
    onInput(event) {
      this.$emit("termChanged", event.target.value);
    },
  },
};
</script>

<style>
.amount-input-wrapper {
  width: 300px;
  display: flex;
}
.year-interest-input-wrapper {
  width: 200px;
  display: flex;
}
.my-input {
  width: 80%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: lpx solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  height: 100%;
}
.text-ending {
  padding: 12px 10px;
  margin: 8px 0;
  border: lpx solid #ccc;
  border-radius: 2px;
}
.flex {
  display: flex;
}
</style>
