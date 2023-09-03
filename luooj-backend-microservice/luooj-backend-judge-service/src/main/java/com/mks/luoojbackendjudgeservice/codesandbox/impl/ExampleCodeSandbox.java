package com.mks.luoojbackendjudgeservice.codesandbox.impl;

import com.mks.luoojbackendjudgeservice.codesandbox.CodeSandbox;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.ExecuteCodeRequest;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.ExecuteCodeResponse;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.JudgeInfo;

import java.util.Arrays;
import java.util.List;

public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String message = "通过";
        Integer status = 1;
        List<String> outputList = Arrays.asList("3", "10");
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage("通过");
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        return ExecuteCodeResponse.builder()
                .message(message)
                .status(status)
                .outputList(outputList)
                .judgeInfo(judgeInfo)
                .build();
    }
}
