// store/modules/dict.js
import { reactive } from 'vue';

//State (状态):  这里定义了一个初始状态，其中 dict 是一个数组，用于存储字典数据。
const state = {
  dict: []
}

// Mutations (突变):  
/*
SET_DICT: 接受一个包含 key 和 value 属性的对象，将其添加到 dict 数组中。
REMOVE_DICT: 根据给定的 key 从 dict 数组中移除对应的项。
CLEAN_DICT: 将 dict 数组重置为空数组。
*/
const mutations = {
  SET_DICT: (state, { key, value }) => {
    if (key !== null && key !== "") {
      state.dict.push({
        key: key,
        value: value
      });
    }
  },
  REMOVE_DICT: (state, key) => {
    try {
      for (let i = 0; i < state.dict.length; i++) {
        if (state.dict[i].key == key) {
          state.dict.splice(i, 1);
          return true;
        }
      }
    } catch (e) {
      // 可以忽略错误
    }
  },
  CLEAN_DICT: (state) => {
    state.dict = [];
  }
};

//Actions (动作):
/*
setDict: 调用 SET_DICT mutation 来设置字典数据。
removeDict: 调用 REMOVE_DICT mutation 来删除指定 key 的字典项。
cleanDict: 调用 CLEAN_DICT mutation 来清空整个字典数组。
*/
const actions = {
  setDict({ commit }, data) {
    commit('SET_DICT', data);
  },
  removeDict({ commit }, key) {
    commit('REMOVE_DICT', key);
  },
  cleanDict({ commit }) {
    commit('CLEAN_DICT');
  }
};

//Export (导出):
export default {
  namespaced: true,
  state,
  mutations,
  actions
};
