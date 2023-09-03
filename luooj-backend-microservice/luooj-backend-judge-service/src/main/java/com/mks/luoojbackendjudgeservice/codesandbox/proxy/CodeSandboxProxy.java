package com.mks.luoojbackendjudgeservice.codesandbox.proxy;


import com.mks.luoojbackendjudgeservice.codesandbox.CodeSandbox;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.ExecuteCodeRequest;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy implements CodeSandbox {

    private CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    public CodeSandbox getCodeSandbox() {
        return codeSandbox;
    }

    public void setCodeSandbox(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：" + (executeCodeResponse != null ? executeCodeResponse.toString() : "null"));
        return executeCodeResponse;
    }
}
