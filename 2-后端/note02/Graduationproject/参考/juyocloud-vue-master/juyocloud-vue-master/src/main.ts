import './styles/main.css'
import './styles/tailwind/index.scss'
import 'babel-polyfill'

import { createApp } from 'vue'
import App from './App.vue'
import axios from 'axios'

const app = createApp(App)

// @ts-ignore
import useGlobalConfigStore from '@/stores/global-config'

import config from '../package.json'
console.log(
    '\n %c JUYOCLOUD ' +
    config.version +
    ' %c https://cloud.juyovo.com \n\n',
    'background: #35495e; padding: 1px; border-radius: 3px 0 0 3px; color: #fff',
    'background: #fadfa3; padding: 1px; border-radius: 0 3px 3px 0; color: #fff'
)

axios
    .get('/juyocloud.config.json')
    .then((res) => {
        useGlobalConfigStore().updateJuyoConfig(res.data)
    })
    .finally(() => {
        app.mount('#app')
    })
