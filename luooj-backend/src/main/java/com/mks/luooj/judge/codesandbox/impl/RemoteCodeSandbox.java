package com.mks.luooj.judge.codesandbox.impl;

import com.mks.luooj.judge.codesandbox.CodeSandbox;
import com.mks.luooj.judge.codesandbox.model.entity.ExecuteCodeRequest;
import com.mks.luooj.judge.codesandbox.model.entity.ExecuteCodeResponse;

public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("执行远程代码沙箱");
        return null;
    }
}
