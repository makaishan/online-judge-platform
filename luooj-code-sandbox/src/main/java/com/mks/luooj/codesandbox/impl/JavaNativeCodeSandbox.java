package com.mks.luooj.codesandbox.impl;

import com.mks.luooj.codesandbox.impl.template.JavaCodeSandboxTemplate;
import com.mks.luooj.codesandbox.model.entity.ExecuteCodeRequest;
import com.mks.luooj.codesandbox.model.entity.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * Java原生实现代码沙箱
 */
@Component
public class JavaNativeCodeSandbox extends JavaCodeSandboxTemplate {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}
