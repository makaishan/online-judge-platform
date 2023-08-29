package com.mks.luooj.codesandbox.securiry;

import java.security.Permission;

/**
 * 默认开启所有权限
 */
public class DefaultSecurityManager extends SecurityManager {

    @Override
    public void checkPermission(Permission perm) {
        System.out.println("默认不做任何权限限制");
        super.checkPermission(perm);
    }
}
