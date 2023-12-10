import './assets/main.css'
import './css/index.css'

import {createApp, ref} from 'vue'
// import App from './App.vue'
import Home from './page/Home.vue'
import router from './router'
import config from './config'

const url = config.post_resource
const data = ref({})
try {
    const response = await fetch(url);
    data.value = await response.json();
    console.log("load data success", data)
} catch (err) {
    console.log("load data error", err)
}

const app = createApp(Home)
app.use(router)
app.provide("global_context", data)
app.mount('#app')
