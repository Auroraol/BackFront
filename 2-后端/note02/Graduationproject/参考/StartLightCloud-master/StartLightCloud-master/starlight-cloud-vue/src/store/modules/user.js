const user = {
    state: {
        username: "",
        user_id: "",
    },
    getters:{   
        getUsername(state){
            return state.username
        }
    },
    mutations: {
        USER_INFO(state,info){
            state.user = info
          }
    },
    actions: {
        saveUserInfo({ commit },data){
            commit('USER_INFO',data)
          }
    }
}

export default user
