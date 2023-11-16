package com.mks.luooj.codesandbox.impl;

import com.mks.luooj.codesandbox.impl.template.CppCodeSandboxTemplate;
import com.mks.luooj.codesandbox.model.entity.ExecuteCodeRequest;
import com.mks.luooj.codesandbox.model.entity.ExecuteCodeResponse;

import org.springframework.stereotype.Component;

@Component
public class CppNativeCodeSandbox extends CppCodeSandboxTemplate {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}
