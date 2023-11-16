package com.mks.luooj.codesandbox;

import com.mks.luooj.codesandbox.controller.CodeSandboxController;
import com.mks.luooj.codesandbox.model.entity.ExecuteCodeRequest;
import com.mks.luooj.codesandbox.model.entity.ExecuteCodeResponse;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // cpp测试
        ExecuteCodeRequest executeCodeRequestCpp = new ExecuteCodeRequest();
        executeCodeRequestCpp.setLanguage("cpp");
        executeCodeRequestCpp.setCode("#include <iostream>\n" +
                "using namespace std;\n" +
                "int main() {\n" +
                "    int x = 0;\n" +
                "    int y = 0;\n" +
                "    cin >> x;\n" +
                "    cin >> y;\n" +
                "    cout << (x + y) << endl;\n" +
                "    return 0;    \n" +
                "}");
        executeCodeRequestCpp.setInputList(Arrays.asList("1 2", "3 4", "-1 -1"));
        // java测试
        ExecuteCodeRequest executeCodeRequestJava = new ExecuteCodeRequest();
        executeCodeRequestJava.setLanguage("java");
        executeCodeRequestJava.setCode("import java.util.Scanner;\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "        int a = scanner.nextInt();\n" +
                "        int b = scanner.nextInt();\n" +
                "        System.out.println(a + b);\n" +
                "    }\n" +
                "}");
        executeCodeRequestJava.setInputList(Arrays.asList("1 2", "3 4", "-1 -1"));
        CodeSandboxController codeSandboxController = new CodeSandboxController();
        ExecuteCodeResponse executeCodeResponse = codeSandboxController.executeCodeByNative(executeCodeRequestJava, null, null);
        System.out.println(executeCodeResponse);
        ExecuteCodeResponse executeCodeResponse1 = codeSandboxController.executeCodeByNative(executeCodeRequestJava, null, null);
        System.out.println(executeCodeResponse1);
    }
}
