import { createApp } from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import './index.css'

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'

library.add(fas);
  
createApp(App).use(router).use(store).component('fa', FontAwesomeIcon).mount('#app');


