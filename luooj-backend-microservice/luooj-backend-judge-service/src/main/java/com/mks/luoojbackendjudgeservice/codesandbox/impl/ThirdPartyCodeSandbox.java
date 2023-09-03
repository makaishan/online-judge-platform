package com.mks.luoojbackendjudgeservice.codesandbox.impl;

import com.mks.luoojbackendjudgeservice.codesandbox.CodeSandbox;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.ExecuteCodeRequest;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.ExecuteCodeResponse;

public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("执行第三方代码沙箱");
        return null;
    }
}
