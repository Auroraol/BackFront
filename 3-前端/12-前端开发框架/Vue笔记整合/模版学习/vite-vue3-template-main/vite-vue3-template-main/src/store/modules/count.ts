import { reactive } from 'vue';

const state = reactive({
  count: "6667777",
});

const getters = {
  getCount: (state) => {
    return state.count;
  },
};

const mutations = {
  increment: (state) => {
    state.count++;
  },
};

const actions = {};

export default {
  state,
  getters,
  mutations,
  actions
};
