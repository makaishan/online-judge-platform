package com.mks.luoojbackendjudgeservice.service;


import com.mks.luoojbackendmodel.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 交给代码沙箱去判题
     * @param questionSubmitId 题目提交id
     * @return QuestionSubmitVO
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
