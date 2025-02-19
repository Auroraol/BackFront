import type {App} from 'vue'
import fileRoutes from '~pages'
import {setupLayouts} from 'virtual:meta-layouts'
import {createRouter, createWebHistory} from 'vue-router'

fileRoutes.push(
    {
        path: '/:storageKey/:fullpath(.*)*',
        meta: {
            layout: 'Layout',
        },
        props: true,
        // @ts-ignore
        component: () => import('@/pages/home/index.vue'),
    }
)

let routes = setupLayouts(fileRoutes)

routes.push({
    path: '/admin',
    redirect: '/admin/site-setting',
})

routes.push({
    path: '/',
    redirect: '/home',
})

export const router = createRouter({
    routes: routes,
    history: createWebHistory(),
})


export default (app: App) => app.use(router)
