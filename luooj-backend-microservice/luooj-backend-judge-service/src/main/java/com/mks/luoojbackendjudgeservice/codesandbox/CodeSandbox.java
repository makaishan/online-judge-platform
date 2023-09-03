package com.mks.luoojbackendjudgeservice.codesandbox;


import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.ExecuteCodeRequest;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.ExecuteCodeResponse;

/**
 * 代码沙箱
 */
public interface CodeSandbox {

    /**
     * 调用代码沙箱执行代码
     * @param executeCodeRequest executeCodeRequest
     * @return ExecuteCodeResponse
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
