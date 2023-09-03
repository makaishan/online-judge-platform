package com.mks.luoojbackendjudgeservice.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.mks.luoojbackendcommon.common.ErrorCode;
import com.mks.luoojbackendcommon.exception.BusinessException;
import com.mks.luoojbackendjudgeservice.codesandbox.CodeSandbox;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.ExecuteCodeRequest;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

public class RemoteCodeSandbox implements CodeSandbox {

    public static final String AUTH_REQUEST_HEADER = "auth";
    public static final String AUTH_REQUEST_SECRET = "secret";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String url = "http://localhost:8122/executeCode/byNative";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseBody = HttpUtil.createPost(url)
                .body(json)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .execute()
                .body();
        if(StringUtils.isBlank(responseBody)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "execute code using remote sandbox error" + responseBody);
        }
        return JSONUtil.toBean(responseBody, ExecuteCodeResponse.class);
    }
}
