import router from "@/router";
import store from "@/store";
import { ACCESS_ENUM } from "@/access/accessEnum";
import { checkAccess } from "@/access/checkAccess";

router.beforeEach(async (to, from, next) => {
  let loginUser = store.state.user.loginUser;
  //如果还未登录过，自动登录
  if (!loginUser || !loginUser.userRole) {
    await store.dispatch("getLoginUser");
  }
  //自动尝试登录后，更新loginUser
  loginUser = store.state.user.loginUser;
  const needAccess = (to.meta?.access as string) ?? ACCESS_ENUM.NOT_LOGIN;
  if (needAccess !== ACCESS_ENUM.NOT_LOGIN) {
    if (
      !loginUser ||
      !loginUser.userRole ||
      loginUser.userRole === ACCESS_ENUM.NOT_LOGIN
    ) {
      next(`/user/login?redirect=${to.fullPath}`);
      return;
    }
    if (!checkAccess(loginUser, needAccess)) {
      next("/noAuth");
      return;
    }
  }
  next();
});
