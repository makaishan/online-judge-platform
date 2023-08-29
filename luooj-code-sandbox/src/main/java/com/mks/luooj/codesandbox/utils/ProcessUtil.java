package com.mks.luooj.codesandbox.utils;

import cn.hutool.core.util.StrUtil;
import com.mks.luooj.codesandbox.model.entity.ExecuteMessage;
import org.springframework.util.StopWatch;

import java.io.*;

public class ProcessUtil {

    /**
     * 参数式运行进程
     * @param runProcess runProcess
     * @param opName opName
     * @return ExecuteMessage
     */
    public static ExecuteMessage runProcessAndGetMessage(Process runProcess, String opName){
        ExecuteMessage executeMessage = new ExecuteMessage();
        StopWatch stopWatch = new StopWatch();
        try {
            //开始计时
            stopWatch.start();
            int exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);
            // 正常退出
            if(exitValue == 0){
                System.out.println(opName + "成功");
                // 获取进程正常输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                StringBuilder compileOutputBuilder = new StringBuilder();
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    compileOutputBuilder.append(compileOutputLine).append("\n");
                }
                executeMessage.setMessage(compileOutputBuilder.toString());
                //停止计时
                stopWatch.stop();
                long lastTaskTimeMillis = stopWatch.getLastTaskTimeMillis();
                executeMessage.setTime(lastTaskTimeMillis);
            }else {
                System.out.println(opName + "失败，错误码：" + exitValue);
                // 获取进程正常输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                StringBuilder compileOutputBuilder = new StringBuilder();
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    compileOutputBuilder.append(compileOutputLine).append("\n");
                }
                executeMessage.setMessage(compileOutputBuilder.toString());
                // 获取进程错误输出
                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                StringBuilder errorCompileOutputBuilder = new StringBuilder();
                String errorCompileOutputLine;
                while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
                    errorCompileOutputBuilder.append(errorCompileOutputLine).append("\n");
                }
                executeMessage.setErrorMessage(errorCompileOutputBuilder.toString());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }

    /**
     * Scanner交互式运行进程
     * @param runProcess runProcess
     * @return ExecuteMessage
     */
    public static ExecuteMessage runInteractionProcessAndGetMessage(Process runProcess, String args) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        StopWatch stopWatch = new StopWatch();
        try {
            // 开始运行
            stopWatch.start();
            // 向控制台输入程序
            OutputStream outputStream = runProcess.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] s = args.split(" ");
            String join = StrUtil.join("\n", (Object) s) + "\n";
            outputStreamWriter.write(join);
            // 相当于按了回车
            outputStreamWriter.flush();
            // 获取进程输出
            InputStream inputStream = runProcess.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder compileOutputBuilder = new StringBuilder();
            // 逐行读取
            String compileOutputLine;
            while ((compileOutputLine = bufferedReader.readLine()) != null) {
                compileOutputBuilder.append(compileOutputLine);
            }
            executeMessage.setMessage(compileOutputBuilder.toString());
            // 释放资源
            outputStreamWriter.close();
            outputStream.close();
            inputStream.close();
            bufferedReader.close();
            // 结束运行
            stopWatch.stop();
            long lastTaskTimeMillis = stopWatch.getLastTaskTimeMillis();
            executeMessage.setTime(lastTaskTimeMillis);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return executeMessage;
    }
}
