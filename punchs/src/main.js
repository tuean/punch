import './assets/main.css'
import './css/index.css'

import { createApp } from 'vue'
// import App from './App.vue'
import Home from './page/Home.vue'
import router from './router'

const app = createApp(Home)

app.use(router)

app.mount('#app')
