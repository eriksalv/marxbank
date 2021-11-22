<template>
  <div class="calculator">
    <text-input type="amount" text="Fast månedlig beløp" />

    <text-input type="amount" text="Engangsbeløp" />

    <text-input type="year" text="Periode" />

    <text-input type="interest" text="Årlig rente" />

    <button
      class="
        bg-green-500
        hover:bg-green-400
        text-white
        font-bold
        py-2
        px-4
        border-b-4 border-green-700
        hover:border-green-500
        rounded
        w-full
        mt-6
      "
      @click="showTotalAmount = true">
      Beregn beløp
    </button>
    <div v-if="showTotalAmount">
      <br />
      <br />
      <hr
        style="
          height: 1px;
          border-width: 0;
          color: gray;
          background-color: gray;
        " />
      <br />
      <p className="text-left text-gray-800">Totalbeløp: kr 1 250 000</p>
      <br />
    </div>
  </div>
</template>

<script>
import TextInput from "@/components/TextInput.vue";

export default {
  name: "Calculator",

  components: {
    TextInput,
  },
  data() {
    return {
      showTotalAmount: false,
    };
  },
};
</script>
