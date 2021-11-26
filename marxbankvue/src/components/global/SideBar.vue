<template>
  <nav
    class="
      fixed
      top-0
      left-0
      h-screen
      w-20
      m-0
      flex flex-col
      bg-gray-900
      text-white
      shadow-lg
      z-10
    ">
    <SideBarIcon icon="home" text="Hjem" to="/" />
    <SideBarIcon icon="wallet" text="Mine kontoer" to="/myAccounts" />
    <SideBarIcon
      icon="exchange-alt"
      text="Overføring"
      to="/createTransaction" />
    <SideBarIcon icon="coins" text="Betalingsoversikt" to="/myTransactions" />
    <SideBarIcon icon="calculator" text="Sparekalkulator" to="/calculator" />
    <SideBarIcon icon="user" text="Min profil" to="/myProfile" />
  </nav>
</template>

<script>
import SideBarIcon from "@/components/global/SideBarIcon.vue";

export default {
  name: "SideBar",
  components: {
    SideBarIcon,
  },
};
</script>

<style></style>
