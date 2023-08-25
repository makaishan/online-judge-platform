package com.mks.luooj.judge.codesandbox;

import com.mks.luooj.judge.codesandbox.model.entity.ExecuteCodeRequest;
import com.mks.luooj.judge.codesandbox.model.entity.ExecuteCodeResponse;

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
