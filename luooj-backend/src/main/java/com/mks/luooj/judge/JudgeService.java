package com.mks.luooj.judge;

import com.mks.luooj.model.entity.QuestionSubmit;

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
