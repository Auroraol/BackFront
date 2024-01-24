//store.ts:
import { createStore } from "vuex";
import user from "./modules/user";
import count from "./modules/count";
import getters from './getters'

const store = createStore({
  modules: {
    user,  // 从 "./MyModule/user" 路径引入名为 "user" 的模块
    count,
  },
  getters
});

export default store;
// 通过引入 src/store/index.ts 就能使用 store里定义的所有
// 如果在main.js 跟vue绑定就能在项目任意地方this.$store.xxx 使用了