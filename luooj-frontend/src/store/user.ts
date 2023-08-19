import { StoreOptions } from "vuex";

export default {
  namespace: true,
  state: () => ({
    loginUser: {
      username: "未登录",
      userRole: "notLogin",
    },
  }),
  actions: {
    // todo 从远程请求登录信息
    getLoginUser({ commit, state }, payload) {
      commit("updateUser", payload);
    },
  },
  mutations: {
    updateUser(state, payload) {
      state.loginUser = payload;
    },
  },
} as StoreOptions<any>;
