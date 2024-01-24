/*
const getters = { ... }: 定义了一个名为 getters 的对象，其中包含 Vuex store 的 getter 函数。
dict: state => state.dict.dict: 定义了一个名为 dict 的 getter 函数，用于从 store 的状态中获取 dict 属性的值。
state 是 Vuex store 的状态对象，包含了所有的状态。
state.dict 获取 store 中名为 dict 的模块（或直接是 store）的状态。
state.dict.dict 获取模块中名为 dict 的属性的值。
*/
// const getters = {
//     dict: state => state.dict.dict,
//     count: state => state.count.count,
// }
// 管理全部状态属性

const getters = {
  dict(state) {
    state.dict.dict;
  },
  count(state) {
    state.count.count;
  },
};

export default getters;
