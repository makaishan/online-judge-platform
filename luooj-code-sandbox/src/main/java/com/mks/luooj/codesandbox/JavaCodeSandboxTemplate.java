package com.mks.luooj.codesandbox;

import cn.hutool.core.io.FileUtil;
import com.mks.luooj.codesandbox.model.entity.ExecuteCodeRequest;
import com.mks.luooj.codesandbox.model.entity.ExecuteCodeResponse;
import com.mks.luooj.codesandbox.model.entity.ExecuteMessage;
import com.mks.luooj.codesandbox.model.entity.JudgeInfo;
import com.mks.luooj.codesandbox.utils.ProcessUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Java代码沙箱模板（默认使用Java原生实现）
 */
@Slf4j
@Component
public abstract class JavaCodeSandboxTemplate implements CodeSandbox {

    public static final String GLOBAL_CODE_DIR_NAME = "tmpCode";
    public static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";
    public static final long TIME_OUT = 5000;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        // 0.获取用户代码和输入用例
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        // 1.将用户的代码保存为文件
        File userCodeFile = saveFile(code);
        if (userCodeFile == null) {
            log.error("create file error.");
            return getErrorResponse(new RuntimeException("create file error."));
        }
        // 2.编译代码得到class文件
        ExecuteMessage executeMessage = compileFile(userCodeFile);
        if (executeMessage == null) {
            log.error("compile error.");
            return getErrorResponse(new RuntimeException("compile error."));
        }
        // 3.执行代码，得到输出结果
        List<ExecuteMessage> executeMessageList = runFile(userCodeFile, inputList);
        if (executeMessageList == null || executeMessageList.size() == 0) {
            log.error("execute code error.");
            return getErrorResponse(new RuntimeException("execute code error."));
        }
        // 4.得到输出结果响应
        ExecuteCodeResponse outputResponse = getOutputResponse(executeMessageList);
        if (outputResponse == null) {
            log.error("get output error.");
            return getErrorResponse(new RuntimeException("get output error."));
        }
        // 5.删除文件
        boolean deleteFile = deleteFile(userCodeFile);
        if (!deleteFile) {
            log.error("delete file error. file: {}", userCodeFile);
        }
        return outputResponse;
    }

    /**
     * 将用户传入的代码保存为文件
     *
     * @param code 用户代码
     * @return 文件
     */
    public File saveFile(String code) {
        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;
        return FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
    }

    /**
     * 编译代码，得到class文件
     *
     * @param userCodeFile 代码文件
     * @return 编译执行信息
     */
    public ExecuteMessage compileFile(File userCodeFile) {
        String compileCmd = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtil.runProcessAndGetMessage(compileProcess, "编译");
            if (executeMessage.getExitValue() != 0) {
                throw new RuntimeException("编译错误");
            }
            return executeMessage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行文件得到输出结果
     *
     * @param userCodeFile 用户代码文件
     * @param inputList    输入用例
     * @return 执行信息列表
     */
    public List<ExecuteMessage> runFile(File userCodeFile, List<String> inputList) {
        String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        for (String input : inputList) {
            String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath, input);
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                // 运行时间保护，超时还未结束，自动销毁进程
                new Thread(() -> {
                    try {
                        Thread.sleep(TIME_OUT);
                        runProcess.destroy();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                ExecuteMessage executeMessage = ProcessUtil.runProcessAndGetMessage(runProcess, "运行");
                executeMessageList.add(executeMessage);
                System.out.println(executeMessage);
            } catch (Exception e) {
                throw new RuntimeException(e + "程序执行异常");
            }
        }
        return executeMessageList;
    }

    /**
     * 得到输出结果响应
     *
     * @param executeMessageList 执行信息列表
     * @return 输出结果响应
     */
    public ExecuteCodeResponse getOutputResponse(List<ExecuteMessage> executeMessageList) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> outputList = new ArrayList<>();
        long maxTime = 0L;
        long maxMemory = 0L;
        for (ExecuteMessage executeMessage : executeMessageList) {
            String errorMessage = executeMessage.getErrorMessage();
            if(StringUtils.isNotBlank(errorMessage)){
                executeCodeResponse.setMessage(errorMessage);
                executeCodeResponse.setStatus(3);
            }
            outputList.add(executeMessage.getMessage());
            Long time = executeMessage.getTime();
            Long memory = executeMessage.getMemory();
            if(time != null) {
                maxTime = Math.max(maxTime, time);
            }
            if(memory != null) {
                maxMemory = Math.max(maxMemory, memory);
            }
        }
        if(outputList.size() == executeMessageList.size()) {
            executeCodeResponse.setStatus(1);
        }
        executeCodeResponse.setOutputList(outputList);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(maxTime);
        judgeInfo.setMemory(maxMemory);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }

    /**
     * 删除文件
     *
     * @param userCodeFile 用户代码文件
     */
    public boolean deleteFile(File userCodeFile) {
        boolean del = true;
        if (userCodeFile.getParentFile() != null) {
            String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
            del = FileUtil.del(userCodeParentPath);
            System.out.println(del ? "删除成功" : "删除失败");

        }
        return del;
    }

    /**
     * 获取错误响应
     *
     * @param e 错误
     * @return ExecuteCodeResponse
     */
    public ExecuteCodeResponse getErrorResponse(Throwable e) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(new ArrayList<>());
        executeCodeResponse.setMessage(e.getMessage());
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setJudgeInfo(new JudgeInfo());
        return executeCodeResponse;
    }
}
