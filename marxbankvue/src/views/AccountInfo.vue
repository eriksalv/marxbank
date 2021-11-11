<template>
  <main class="flex flex-row flex-wrap max-w-[80%] justify-center items-center">
    <transition name="fade" appear>
      <div class="modalOverlay" v-if="showModal" @click="showModal = false"></div>
    </transition>
    <transition name="slide" appear>
      <div class="modal" v-if="showModal">
        <h1 class="title">Sett inn eller ta ut penger</h1>
        <h1>Kroner</h1>
        <input type="number" placeholder="0" class="input" v-model="amount">
        <button class="button" @click="handleDeposit">Sett inn</button>
        <button class="button" @click="handleWithdraw">Ta ut</button>
      </div>
    </transition>
    <div class="bg-white shadow overflow-hidden sm:rounded-lg min-w-full">
    <div>
    <h1 class="title">
      {{selectedAccount.name}}
    </h1>
    <p class="mt-1 text-sm text-gray-500 text-center">
      Detaljer
    </p>
    </div>
    <div class="mt-5 border-t border-gray-200">
    <dl class="divide-y divide-gray-200">
      <div class="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4">
        <dt class="text-sm font-medium text-gray-500">
          Disponibelt beløp
        </dt>
        <dd class="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
          <span class="flex-grow" @click="showModal = true">{{selectedAccount.balance}} kr</span>
        </dd>
      </div>
      <div class="py-4 sm:grid sm:py-5 sm:grid-cols-3 sm:gap-4">
        <dt class="text-sm font-medium text-gray-500">
          Kontotype
        </dt>
        <dd class="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
          <span class="flex-grow">{{selectedAccount.type}}</span>
        </dd>
      </div>
      <div class="py-4 sm:grid sm:py-5 sm:grid-cols-3 sm:gap-4">
        <dt class="text-sm font-medium text-gray-500">
          Kontonummer
        </dt>
        <dd class="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
          <span class="flex-grow">{{selectedAccount.accNumber}}</span>
        </dd>
      </div>
      <div class="py-4 sm:grid sm:py-5 sm:grid-cols-3 sm:gap-4">
        <dt class="text-sm font-medium text-gray-500">
          Rente
        </dt>
        <dd class="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
          <span class="flex-grow">{{selectedAccount.interest}} %</span>
        </dd>
      </div>
    </dl>
  </div>
  </div>
  <h1 class="title">Nylig aktivitet</h1>
  <TransactionList :transactions="filterTransactionsByAccount(selectedAccount.id)" class="min-w-max"/>
</main>
</template>

<script>
import {mapActions, mapGetters} from 'vuex';
import TransactionList from '../components/TransactionList.vue'

export default {
    props: {
      id: Number
    },
    computed: {
        ...mapGetters(['getAccountById', 'filterTransactionsByAccount'])
    },
    components: {
        TransactionList
    },
    methods: {
      ...mapActions(["fetchById", "deposit", "withdraw"]),
      async handleDeposit() {
        const request = { amount: this.amount, accountId: this.selectedAccount.id };
        await this.deposit(request).catch(err => console.log(err));
        this.selectedAccount = this.getAccountById(parseInt(this.id));
        this.showModal = false;
      },
      async handleWithdraw() {
        const request = { amount: this.amount, accountId: this.selectedAccount.id };
        await this.withdraw(request).catch(err => console.log(err));
        this.selectedAccount = this.getAccountById(parseInt(this.id));
        this.showModal = false
      }
    },
    data() {
        return {
            selectedAccount: Object,
            showModal: false,
            amount: Number,
        }
    },
    created() {
        this.fetchById(this.id)
        this.selectedAccount = this.getAccountById(parseInt(this.id))
    }
}
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