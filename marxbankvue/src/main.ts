import { createApp } from 'vue'
import App from './App.vue'
import HelloWorldComponent from './components/HelloWorld.vue'
import { createStore } from 'vuex'

// Create a new store instance.
const store = createStore({
  state: {
      count: 0
  },
  mutations: {
    increment (state) {
      state.count++
    }
  }
})
const app = createApp(App)
const vm = app.mount('#app')

// Install the store instance as a plugin
app.use(store)

store.commit('increment')
store.commit('increment')
store.commit('increment')

console.log(store.state.count) // -> 3

