package com.mks.luoojbackendjudgeservice.codesandbox.factory;

import com.mks.luoojbackendjudgeservice.codesandbox.CodeSandbox;
import com.mks.luoojbackendjudgeservice.codesandbox.impl.ExampleCodeSandbox;
import com.mks.luoojbackendjudgeservice.codesandbox.impl.RemoteCodeSandbox;
import com.mks.luoojbackendjudgeservice.codesandbox.impl.ThirdPartyCodeSandbox;
import com.mks.luoojbackendmodel.model.codesandboxmodel.enums.CodeSandboxTypeEnum;

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
