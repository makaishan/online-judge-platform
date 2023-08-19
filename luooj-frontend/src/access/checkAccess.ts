import { ACCESS_ENUM } from "@/access/accessEnum";

/**
 * 判断登录用户有无权限
 * @param loginUser 登录用户
 * @param needAccess 需要权限
 * @return boolean 有无权限
 */
export const checkAccess = (
  loginUser: any,
  needAccess = ACCESS_ENUM.NOT_LOGIN
) => {
  //获取当前登录用户具有的权限，如果没有登录用户，则默认为未登录
  const loginUserAccess = loginUser?.userRole ?? ACCESS_ENUM.NOT_LOGIN;
  // 如果不需要任何权限
  if (needAccess === ACCESS_ENUM.NOT_LOGIN) {
    return true;
  }
  // 如果需要用户登录
  if (needAccess === ACCESS_ENUM.USER) {
    if (loginUserAccess === ACCESS_ENUM.NOT_LOGIN) {
      return false;
    }
  }
  // 如果需要用户具有管理员权限
  if (needAccess === ACCESS_ENUM.ADMIN) {
    if (loginUserAccess !== ACCESS_ENUM.ADMIN) {
      return false;
    }
  }
  return true;
};
