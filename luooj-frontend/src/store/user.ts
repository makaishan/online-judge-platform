import { StoreOptions } from "vuex";
import { ACCESS_ENUM } from "@/access/accessEnum";

export default {
  namespace: true,
  state: () => ({
    loginUser: {
      username: "未登录",
      userRole: ACCESS_ENUM.NOT_LOGIN,
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
