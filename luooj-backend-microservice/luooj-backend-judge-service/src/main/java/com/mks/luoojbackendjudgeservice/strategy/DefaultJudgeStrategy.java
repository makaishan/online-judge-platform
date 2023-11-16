package com.mks.luoojbackendjudgeservice.strategy;

import cn.hutool.json.JSONUtil;

import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.JudgeInfo;
import com.mks.luoojbackendmodel.model.dto.question.JudgeCase;
import com.mks.luoojbackendmodel.model.dto.question.JudgeConfig;
import com.mks.luoojbackendmodel.model.entity.Question;
import com.mks.luoojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.mks.luoojbackendserviceclient.service.QuestionFeignClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Component
public class DefaultJudgeStrategy implements JudgeStrategy {

    @Resource
    QuestionFeignClient questionFeignClient;

    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        String message = judgeInfo.getMessage();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();

        // 根据沙箱执行结果判断题目是否通过
        JudgeInfoMessageEnum judgeInfoMessageEnum;
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        // 判断程序是否编译成功
        if (StringUtils.isNotBlank(message) && message.equals(JudgeInfoMessageEnum.COMPILE_ERROR.getValue())) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.COMPILE_ERROR;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            updateAcceptedRate(question, false);
            return judgeInfoResponse;
        }
        // 判断程序是否运行成功
        if (StringUtils.isNotBlank(message) && message.equals(JudgeInfoMessageEnum.RUNTIME_ERROR.getValue())) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.RUNTIME_ERROR;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            updateAcceptedRate(question, false);
            return judgeInfoResponse;
        }
        // 先判断输出结果是否与输入用例数量相同
        if (outputList == null || outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            updateAcceptedRate(question, false);
            return judgeInfoResponse;
        }
        // 再逐一判断输出结果与输出用例是否都相同
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!Objects.equals(judgeCase.getOutput(), outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                updateAcceptedRate(question, false);
                return judgeInfoResponse;
            }
        }
        // 判断题目限制是否满足
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long timeLimit = judgeConfig.getTimeLimit();
        Long memoryLimit = judgeConfig.getMemoryLimit();
        // 内存限制
        if (memory == null || memory > memoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            updateAcceptedRate(question, false);
            return judgeInfoResponse;
        }
        // 时间限制
        if (time == null || time > timeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            updateAcceptedRate(question, false);
            return judgeInfoResponse;
        }
        judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        updateAcceptedRate(question, true);
        return judgeInfoResponse;
    }

    @Override
    public boolean updateAcceptedRate(Question question, boolean isAccepted) {
        question.setSubmitNum(question.getSubmitNum() + 1);
        if (isAccepted) {
            question.setAcceptedNum(question.getAcceptedNum() + 1);
        }
        return questionFeignClient.updateQuestionById(question);
    }
}
