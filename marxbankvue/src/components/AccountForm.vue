<template>
  <div class="w-1/4">
      <h1 class="title">Ny konto</h1>
      <h1>Kontonavn</h1>
      <input type="text" className="input" placeholder="Ny konto" ref="accountName">
      <h1>Kontotype</h1>
      <select class="select" name="accountTypes" id="accountTypes" ref="accountType">
        <option value="Brukskonto">Brukskonto</option>
        <option value="Sparekonto">Sparekonto</option>
        <option value="Marxkonto">Marxkonto</option>
        <option value="Kredittkonto">Kredittkonto</option>
      </select>
      <button class="button" @click="createAccount">Opprett konto</button>
    </div>
</template>

<script>
export default {
    name: "AccountForm",
    methods: {
        createAccount() {
            //TODO: lag konto
            console.log(this.$refs.accountName.value)
        }
    }
}
</script>

<style>

</style>