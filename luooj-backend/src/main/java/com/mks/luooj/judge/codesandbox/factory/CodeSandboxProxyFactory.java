package com.mks.luooj.judge.codesandbox.factory;

import com.mks.luooj.judge.codesandbox.CodeSandbox;
import com.mks.luooj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.mks.luooj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.mks.luooj.judge.codesandbox.impl.ThirdPartyCodeSandbox;
import com.mks.luooj.judge.codesandbox.model.enums.CodeSandboxTypeEnum;
import com.mks.luooj.judge.codesandbox.proxy.CodeSandboxProxy;

/**
 * 代码沙箱代理工厂
 */
public class CodeSandboxProxyFactory {

    public static CodeSandbox newCodeSandboxProxy(String type){
        if(type.equals(CodeSandboxTypeEnum.EXAMPLE.getValue())){
            return new CodeSandboxProxy(new ExampleCodeSandbox());
        }
        if(type.equals(CodeSandboxTypeEnum.REMOTE.getValue())){
            return new CodeSandboxProxy(new RemoteCodeSandbox());
        }
        if(type.equals(CodeSandboxTypeEnum.THIRD_PARTY.getValue())){
            return new ThirdPartyCodeSandbox();
        }
        return new CodeSandboxProxy(new ExampleCodeSandbox());
    }
}
