package com.mks.luoojbackendjudgeservice.service.impl;

import cn.hutool.json.JSONUtil;
import com.mks.luoojbackendcommon.common.ErrorCode;
import com.mks.luoojbackendcommon.exception.BusinessException;
import com.mks.luoojbackendjudgeservice.codesandbox.CodeSandbox;
import com.mks.luoojbackendjudgeservice.codesandbox.factory.CodeSandboxProxyFactory;
import com.mks.luoojbackendjudgeservice.manager.JudgeManager;
import com.mks.luoojbackendjudgeservice.service.JudgeService;
import com.mks.luoojbackendjudgeservice.strategy.JudgeContext;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.ExecuteCodeRequest;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.ExecuteCodeResponse;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.JudgeInfo;
import com.mks.luoojbackendmodel.model.dto.question.JudgeCase;
import com.mks.luoojbackendmodel.model.entity.Question;
import com.mks.luoojbackendmodel.model.entity.QuestionSubmit;
import com.mks.luoojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.mks.luoojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 获取题目提交信息
        if(questionSubmitId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目提交Id不合法");
        }
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if(questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交信息不存在");
        }
        // 如果当前题目提交的状态不为等待中，则无需判题
        if(!Objects.equals(questionSubmit.getStatus(), QuestionSubmitStatusEnum.WAITING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "当前题目提交正在判题，请勿重复提交");
        }
        // 获取需要判题的题目信息
        long questionId = questionSubmit.getQuestionId();
        if(questionId < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目Id不合法");
        }
        Question question = questionFeignClient.getQuestionById(questionId);
        if(question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 修改题目提交的状态信息为判题中
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        if (!questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交状态更新错误");
        }
        // 调用代码沙箱，拿到执行结果
        CodeSandbox codeSandbox = CodeSandboxProxyFactory.newCodeSandboxProxy(type);
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        // 根据沙箱执行结果判断题目是否通过
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(judgeInfo);
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfoResponse = judgeManager.doJudge(judgeContext);
        // 修改题目提交的状态信息为成功
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoResponse));
        if (!questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交状态更新错误");
        }
        return questionFeignClient.getQuestionSubmitById(questionSubmitId);
    }
}
