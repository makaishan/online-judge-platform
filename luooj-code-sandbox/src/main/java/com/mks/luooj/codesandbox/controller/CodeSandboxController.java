package com.mks.luooj.codesandbox.controller;

import com.mks.luooj.codesandbox.impl.CppNativeCodeSandbox;
import com.mks.luooj.codesandbox.impl.JavaDockerCodeSandbox;
import com.mks.luooj.codesandbox.impl.JavaNativeCodeSandbox;
import com.mks.luooj.codesandbox.model.entity.ExecuteCodeRequest;
import com.mks.luooj.codesandbox.model.entity.ExecuteCodeResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class CodeSandboxController {

    public static final String AUTH_REQUEST_HEADER = "auth";
    public static final String AUTH_REQUEST_SECRET = "secret";

    @Resource
    private JavaNativeCodeSandbox javaNativeCodeSandbox;
    @Resource
    private CppNativeCodeSandbox cppNativeCodeSandbox;
    @Resource
    private JavaDockerCodeSandbox javaDockerCodeSandbox;

    @PostMapping("/executeCode/byNative")
    public ExecuteCodeResponse executeCodeByNative(@RequestBody ExecuteCodeRequest executeCodeRequest,
                                                   HttpServletRequest httpServletRequest,
                                                   HttpServletResponse httpServletResponse) {
        String header = httpServletRequest.getHeader(AUTH_REQUEST_HEADER);
        if (!AUTH_REQUEST_SECRET.equals(header)) {
            httpServletResponse.setStatus(403);
            return null;
        }
        if (executeCodeRequest == null) {
            throw new RuntimeException("请求参数为空。");
        }
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        if (executeCodeRequest.getLanguage().equals("java")) {
            executeCodeResponse = javaNativeCodeSandbox.executeCode(executeCodeRequest);
        } else if (executeCodeRequest.getLanguage().equals("cpp")) {
            executeCodeResponse = cppNativeCodeSandbox.executeCode(executeCodeRequest);
        }
        return executeCodeResponse;
    }

    @PostMapping("/executeCode/byDocker")
    public ExecuteCodeResponse executeCodeByDocker(@RequestBody ExecuteCodeRequest executeCodeRequest) {
        if (executeCodeRequest == null) {
            throw new RuntimeException("请求参数为空。");
        }
        return javaDockerCodeSandbox.executeCode(executeCodeRequest);
    }
}
