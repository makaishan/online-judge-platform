package com.mks.luooj.judge.codesandbox.factory;

import com.mks.luooj.judge.codesandbox.CodeSandbox;
import com.mks.luooj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.mks.luooj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.mks.luooj.judge.codesandbox.impl.ThirdPartyCodeSandbox;
import com.mks.luooj.judge.codesandbox.model.enums.CodeSandboxTypeEnum;

/**
 * 代码沙箱工厂
 */
public class CodeSandboxFactory {

    public static CodeSandbox newCodeSandbox(String type){
        if(type.equals(CodeSandboxTypeEnum.EXAMPLE.getValue())){
            return new ExampleCodeSandbox();
        }
        if(type.equals(CodeSandboxTypeEnum.REMOTE.getValue())){
            return new RemoteCodeSandbox();
        }
        if(type.equals(CodeSandboxTypeEnum.THIRD_PARTY.getValue())){
            return new ThirdPartyCodeSandbox();
        }
        return new ExampleCodeSandbox();
    }
}
