<template>
  <div>
    <form action="" class="flex flex-col w-full items-center">
      <div class="input-style">
        <label for="username">Username</label>
        <input
          id="username"
          v-model="username"
          class="input"
          type="text"
          name="username"
          :disabled="authStatus === 'loading'" />
      </div>
      <div class="input-style">
        <label for="email">Email</label>
        <input
          id="email"
          v-model="email"
          class="input"
          type="email"
          name="email"
          :disabled="authStatus === 'loading'" />
      </div>
      <div class="input-style">
        <label for="password">Password</label>
        <input
          id="password"
          v-model="password"
          class="input"
          type="password"
          name="password"
          :disabled="authStatus === 'loading'" />
      </div>
      <div class="input-style">
        <label for="repeatPassword">Repeat password</label>
        <input
          id="repeatPassword"
          v-model="repeatPassword"
          class="input"
          type="password"
          name="repeatPassword"
          :disabled="authStatus === 'loading'" />
      </div>
      <div v-if="error" class="errorText">
        {{ errorMessage }}
      </div>
      <button
        class="
          mx-auto
          my-5
          w-2/5
          bg-white
          rounded-sm
          drop-shadow-md
          relative
          h-16
          block
          border-2 border-white
          hover:border-2 hover:border-red-500
          duration-300
        "
        @click.prevent="register">
        <img
          src="/Hammer.svg"
          :class="
            authStatus === 'loading'
              ? 'communismIcon right-0 loading'
              : 'communismIcon right-0 transform-gpu rotate-90'
          " />
        <p class="inline-block font-bold text-2xl z-10 relative">Register</p>
      </button>
    </form>
  </div>
</template>
<script lang="ts">
import { defineComponent } from "vue";
import { mapGetters, mapActions } from "vuex";
import { SignUpRequest } from "../types/types";

export default defineComponent({
  name: "RegisterComponent",
  data() {
    return {
      username: "",
      email: "",
      password: "",
      repeatPassword: "",
      errorMessage: "",
      error: false,
    };
  },
  computed: {
    ...mapGetters(["authStatus", "getStatusCode"]),
  },
  methods: {
    ...mapActions(["signup"]),
    register(): void {
      this.error = false;
      this.errorMessage = "";

      /**
       * Some basic client side validation to prevent unnecessary
       * requests to backend
       */
      if (this.username.length < 4) {
        this.errorMessage = "Username is too short";
        this.error = true;
        return;
      } else if (this.username.match(/[^a-zA-Z ]/g)) {
        this.errorMessage = "Username contains illegal characters";
        this.error = true;
        return;
      } else if (this.password.length < 4) {
        this.errorMessage = "Password is too short";
        this.error = true;
        return;
      } else if (this.password !== this.repeatPassword) {
        this.errorMessage = "Passwords does not match";
        this.error = true;
        return;
      }

      const request: SignUpRequest = {
        username: this.username,
        password: this.password,
        email: this.email,
      };

      this.signup(request)
        .then(() => {
          this.$router.push("/");
          return;
        })
        .catch((err: Error) => {
          this.errorMessage = err.message;
          this.error = true;
        });
    },
  },
});
</script>
<style scoped>
.input-style {
  @apply flex flex-col m-1 mt-5 items-start w-3/5;
}

.input-style label {
  @apply text-red-500 text-xl font-bold;
}

.communismIcon {
  @apply w-12 absolute top-3 z-0 origin-center;
}

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
