package com.mks.luooj.codesandbox.securiry;

import java.security.Permission;

/**
 * 禁用所有权限
 */
public class DenySecurityManager extends SecurityManager{

    @Override
    public void checkPermission(Permission perm) {
        throw new SecurityException("权限不足" + perm);
    }
}
