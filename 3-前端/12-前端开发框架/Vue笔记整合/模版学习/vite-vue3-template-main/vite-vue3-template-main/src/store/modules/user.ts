const state = {
  test: "测试分模块2"
}

const mutations = {
  getTest(state) {
    state.test = "--------------------------------------------------改变分模块数据";
  }
}

const actions = {
  changeTest({ commit }) {
    commit("getTest");
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}