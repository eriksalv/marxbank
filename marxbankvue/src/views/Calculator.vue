<template>
  <div class="calculator">
    <main class="text-on-same-line">
      <TextInput
        id="i1"
        type="amount"
        text="Fast månedlig beløp"
        @termChanged="onInputChanged1" />
      <TextInput
        id="i3"
        type="year"
        text="Periode"
        @termChanged="onInputChanged3" />
    </main>
    <br />
    <main class="text-on-same-line">
      <TextInput
        id="i2"
        type="amount"
        text="Engangsbeløp"
        @termChanged="onInputChanged2" />
      <TextInput
        id="i4"
        type="interest"
        text="Årlig rente"
        @termChanged="onInputChanged4" />
    </main>
    <button class="button w-80" @click="calc">Beregn beløp</button>
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
      <p className="text-center text-gray-800 text-2xl">
        Totalbeløp: {{ amount }}
      </p>
      <br />
    </div>
  </div>
</template>

<script>
import TextInput from "../components/TextInput.vue";

export default {
  name: "Calculator",

  components: {
    TextInput,
  },
  data() {
    return {
      showTotalAmount: false,
      input1: "",
      input2: "",
      input3: "",
      input4: "",
      amount: null,
    };
  },
  methods: {
    onInputChanged1(input) {
      this.input1 = input;
    },
    onInputChanged2(input) {
      this.input2 = input;
    },
    onInputChanged3(input) {
      this.input3 = input;
    },
    onInputChanged4(input) {
      this.input4 = input;
    },
    calc() {
      this.showTotalAmount = true;

      const monthlyAmount = this.input1;
      const lumpAmount = this.input2;
      const period = this.input3;
      const interestRate = this.input4;

      let unit = "kr  ";

      if (period == 0) {
        let textString = "  (engangsbeløp)";
        let sum = Math.round(lumpAmount);
        this.amount = unit + sum.toLocaleString() + textString;
      } else {
        let lumpAmountSum = lumpAmount;
        let monthlyAmountSum = 0;

        let interestRateFactor = interestRate / 100;

        for (let i = 0; i < period; i++) {
          monthlyAmountSum *= 1 + interestRateFactor;
          lumpAmountSum *= 1 + interestRateFactor;

          for (let j = 1; j <= 12; j++) {
            monthlyAmountSum +=
              monthlyAmount * (1 + (j * interestRateFactor) / 12);
          }
        }

        let sum = lumpAmountSum + monthlyAmountSum;

        if (sum >= 1000000000000) {
          this.amount = " beløpet er utenfor rekkevidde!"; // over or equal to a trillion kr.
        } else {
          let integerSum = Math.round(sum);
          this.amount = unit + integerSum.toLocaleString();
        }
      }
    },
  },
};
</script>
