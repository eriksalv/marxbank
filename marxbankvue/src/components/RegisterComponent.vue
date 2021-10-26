<template>
    <div>
        <form action="" class="flex flex-col w-full items-center">
            <div class="input-style">
                <label for="username">Username</label>
                <input type="text" name="username" id="username" v-model="username" :disabled="loading">
            </div>
            <div class="input-style">
                <label for="email">Email</label>
                <input type="email" name="email" id="email" v-model="email" :disabled="loading">
            </div>
            <div class="input-style">
                <label for="password">Password</label>
                <input type="password" name="password" id="password" v-model="password" :disabled="loading">
            </div>
            <div class="input-style">
                <label for="repeatPassword">Repeat password</label>
                <input type="password" name="repeatPassword" id="repeatPassword" v-model="repeatPassword" :disabled="loading">
            </div>
            <button @click.prevent="register" class="mx-auto my-5 w-2/5 bg-white rounded-sm drop-shadow-md relative h-16 block border-2 border-white hover:border-2 hover:border-red-500 duration-300"><img src="/Hammer.svg" v-bind:class="(loading)? 'communismIcon right-0 loading' : 'communismIcon right-0 transform-gpu rotate-90'"><p class="inline-block font-bold text-2xl z-10 relative">Register</p></button>
        </form>
    </div>
</template>
<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
    name: 'RegisterComponent',
    data() {
        return {
            username: "",
            email: "",
            password: "",
            repeatPassword: "",
            loading: false
        }
    },
    methods: {
        register(): void {
            console.log({username: this.username, email: this.email, password: this.password, repeatPassword: this.repeatPassword});
            this.loading = true;
        }
    }
})
</script>
<style scoped>
    .input-style {
        @apply flex flex-col m-1 mt-5 items-start w-3/5;
    }

    .input-style label {
        @apply text-red-500 text-xl font-bold;
    }

    .input-style input {
        @apply w-full h-8 p-1 rounded-sm drop-shadow-sm;
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
