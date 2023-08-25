package com.mks.luooj.judge.codesandbox;

import com.mks.luooj.judge.codesandbox.factory.CodeSandboxFactory;
import com.mks.luooj.judge.codesandbox.factory.CodeSandboxProxyFactory;
import com.mks.luooj.judge.codesandbox.model.entity.ExecuteCodeRequest;
import com.mks.luooj.judge.codesandbox.model.enums.CodeSandboxTypeEnum;
import com.mks.luooj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
class CodeSandboxTest {

    @Test
    void test(){
        CodeSandbox codeSandbox = CodeSandboxFactory.newCodeSandbox(CodeSandboxTypeEnum.EXAMPLE.getValue());
        String code = "int main(){}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        codeSandbox.executeCode(executeCodeRequest);
    }

    @Test
    void testProxy(){
        CodeSandbox codeSandbox = CodeSandboxProxyFactory.newCodeSandboxProxy(CodeSandboxTypeEnum.EXAMPLE.getValue());
        String code = "int main(){}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        codeSandbox.executeCode(executeCodeRequest);
    }

}