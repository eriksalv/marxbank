<template>
    <div class="w-screen h-screen">
        <h1 class="text-red-500 font-bold text-4xl">The Revolutionaries' Bank</h1>
        <div class="flex bg-gray-500 w-1/4 h-auto mx-auto mt-40 mb-auto drop-shadow-lg rounded-md flex-col">
            <div v-if="!register">
                <form action="" class="flex flex-col w-full items-center" method="POST">
                    <div class="input-style">
                        <label for="username">Username</label>
                        <input type="text" name="username" id="username" v-model="username" :disabled="loading">
                    </div>
                    <div class="input-style">
                        <label for="password">Password</label>
                        <input type="password" name="password" id="password" v-model="password" :disabled="loading">
                    </div>
                    <button @click.prevent="login" class="mt-5 w-2/5 bg-white rounded-sm drop-shadow-md relative h-16 block border-2 border-white hover:border-2 hover:border-red-500 duration-300"><img src="/Sickle.svg" alt="" v-bind:class="(loading) ? 'communismIcon left-1 loading' : 'communismIcon left-1'"><p class="inline-block font-bold text-2xl z-10 relative">Login</p></button>
                </form>
                <button @click.prevent="createNewUser" class="mx-auto my-5 w-2/5 bg-white rounded-sm drop-shadow-md relative h-16 block border-2 border-white hover:border-2 hover:border-red-500 duration-300"><img src="/Hammer.svg" class="communismIcon right-0 transform-gpu rotate-90"><p class="inline-block font-bold text-2xl z-10 relative">Register</p></button>
            </div>
            <div v-if="register">
                <RegisterComponent/>
                <button @click="back" v-if="register" class="mx-auto mb-5 w-2/5 bg-white rounded-sm drop-shadow-md relative h-16 block border-2 border-white hover:border-2 hover:border-red-500 duration-300"><p class="font-bold text-2xl z-10 relative">Back</p></button>
            </div>
        </div>
    </div>
</template>
<script lang="ts">
import { defineComponent } from 'vue';
import RegisterComponent from './RegisterComponent.vue';

export default defineComponent({
    name: 'LoginComponent',
    components: {
        RegisterComponent
    },
    data () {
        return {
            username: "",
            password: "",
            loading: false,
            register: false
        }
    },
    methods: {
        login(): void {
            console.log({username: this.username, password: this.password});
            this.loading = true;
        },

        createNewUser(): void {
            this.register = true;
        },

        back(): void {
            this.register = false;
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