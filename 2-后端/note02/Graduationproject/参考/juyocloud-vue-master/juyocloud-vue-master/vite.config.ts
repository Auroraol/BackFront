import {resolve} from 'path'
import {defineConfig} from 'vite'
import Tov from './presets/tov'

// https://vitejs.dev/config/
// @ts-ignore
export default defineConfig({
    server: {
        port: 3000,
        proxy: {
            '/api': {
                target: 'http://127.0.0.1:3300', // 实际请求地址
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, ""),
            },
        },
    },
    css: {
        preprocessorOptions: {
            scss: {
                additionalData: `@use "@/styles/element/custom.scss" as *;`,
            },
        },
    },
    resolve: {
        alias: {
            '@': `${resolve(__dirname, 'src')}/`,
        },
    },
    plugins: [
        Tov()
    ],
    define: {
        'process.env': {},
    },
    build: {
        rollupOptions: {
            output: {
                // 最⼩化拆分包
                manualChunks: (id) => {
                    if (id.includes('node_modules')) {
                        return id.toString().split('node_modules/')[1].split('/')[0].toString();
                    }
                },
                // ⽤于从⼊⼝点创建的块的打包输出格式[name]表⽰⽂件名,[hash]表⽰该⽂件内容hash值
                entryFileNames: 'js/[name].[hash].js',
                // ⽤于命名代码拆分时创建的共享块的输出命名
                chunkFileNames: 'js/[name].[hash].js',
                // ⽤于输出静态资源的命名，[ext]表⽰⽂件扩展名
                assetFileNames: '[ext]/[name].[hash].[ext]',
            },
        }
    }
})
