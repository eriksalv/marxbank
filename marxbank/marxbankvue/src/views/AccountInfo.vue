<template>
  <main class="flex flex-row flex-wrap max-w-[80%] justify-center items-center">
    <transition name="fade" appear>
      <div v-if="showModal" class="modalOverlay" @click="showModal = false" />
    </transition>
    <transition name="slide" appear>
      <div v-if="showModal" class="modal">
        <h1 class="title">Sett inn eller ta ut penger</h1>
        <h1>Kroner</h1>
        <input v-model="amount" type="number" placeholder="0" class="input" />
        <p v-if="error" class="text-red-600">{{ errorMsg }}</p>
        <button id="deposit" class="button" @click="handleDeposit">
          Sett inn
        </button>
        <button id="withdraw" class="button" @click="handleWithdraw">
          Ta ut
        </button>
      </div>
    </transition>
    <div class="bg-white shadow overflow-hidden sm:rounded-lg min-w-full">
      <div>
        <h1 class="title">
          {{ selectedAccount.name }}
        </h1>
        <p class="mt-1 text-sm text-gray-500 text-center">Detaljer</p>
      </div>
      <div class="mt-5 border-t border-gray-200">
        <dl class="divide-y divide-gray-200">
          <div class="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4">
            <dt class="text-sm font-medium text-gray-500">Disponibelt beløp</dt>
            <dd class="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
              <span class="flex-grow">{{ selectedAccount.balance }} kr</span>
            </dd>
          </div>
          <div class="py-4 sm:grid sm:py-5 sm:grid-cols-3 sm:gap-4">
            <dt class="text-sm font-medium text-gray-500">Kontotype</dt>
            <dd class="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
              <span class="flex-grow">{{ selectedAccount.type }}</span>
            </dd>
          </div>
          <div class="py-4 sm:grid sm:py-5 sm:grid-cols-3 sm:gap-4">
            <dt class="text-sm font-medium text-gray-500">Id</dt>
            <dd class="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
              <span class="flex-grow">{{ selectedAccount.id }}</span>
            </dd>
          </div>
          <div class="py-4 sm:grid sm:py-5 sm:grid-cols-3 sm:gap-4">
            <dt class="text-sm font-medium text-gray-500">Rente</dt>
            <dd class="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
              <span class="flex-grow">{{ selectedAccount.interest }} %</span>
            </dd>
          </div>
        </dl>
      </div>
    </div>
    <button
      id="showModal"
      class="button w-1/6 rounded-3xl text-5xl font-bold"
      @click="showModal = true">
      +
    </button>
    <h1 class="title w-full">Nylig aktivitet</h1>
    <TransactionList
      :transactions="filterTransactionsByAccount(selectedAccount.id)"
      class="min-w-max" />
  </main>
</template>

<script>
import { defineComponent } from "@vue/runtime-core";
import { mapActions, mapGetters } from "vuex";
import TransactionList from "../components/TransactionList.vue";

export default defineComponent({
  components: {
    TransactionList,
  },
  props: {
    id: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      selectedAccount: Object,
      showModal: false,
      amount: Number,
      error: false,
      errorMsg: "",
    };
  },
  computed: {
    ...mapGetters(["getAccountById", "filterTransactionsByAccount"]),
  },
  /**
   * fetches the selected account from api to make sure properties
   * of the selected account are up to date, and calls setSelectedAccount
   */
  async created() {
    await Promise.all([
      this.fetchAccountById(this.id),
      this.fetchAccountsByTransactions(),
      this.fetchTransactionsByAccount(this.id),
    ]);
    this.setSelectedAccount(this.id);
  },
  methods: {
    ...mapActions([
      "fetchAccountById",
      "deposit",
      "withdraw",
      "fetchTransactionsByAccount",
      "fetchAccountsByTransactions",
    ]),
    /**
     * creates a deposit request and uses the deposit-action. Then updates
     * page by setting the selectedAccount again, and hides the modal.
     */
    async handleDeposit() {
      this.error = false;
      const request = {
        amount: this.amount,
        accountId: this.selectedAccount.id,
      };
      await this.deposit(request)
        .then(() => {
          this.setSelectedAccount(this.id);
          this.showModal = false;
        })
        .catch((err) => {
          this.error = true;
          this.errorMsg = err.message;
        });
    },
    /**
     * creates a withdraw request and uses the withdraw-action. Then updates
     * page by setting the selectedAccount again, and hides the modal.
     */
    async handleWithdraw() {
      this.error = false;
      const request = {
        amount: this.amount,
        accountId: this.selectedAccount.id,
      };
      await this.withdraw(request)
        .then(() => {
          this.setSelectedAccount(this.id);
          this.showModal = false;
        })
        .catch((err) => {
          this.error = true;
          this.errorMsg = err.message;
        });
    },
    /**
     * updates the selectedAccount data property using the getAccountById-getter
     * @param id id of account
     */
    setSelectedAccount(id) {
      this.selectedAccount = this.getAccountById(parseInt(id));
    },
  },
});
</script>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.slide-enter-active,
.slide-leave-active {
  transition: transform 0.5s ease;
}

.slide-enter-from,
.slide-leave-to {
  transform: translateY(-50%) translateX(100vw);
}
</style>
