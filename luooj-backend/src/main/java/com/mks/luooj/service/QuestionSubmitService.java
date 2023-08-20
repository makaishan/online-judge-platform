package com.mks.luooj.service;

import com.mks.luooj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.mks.luooj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mks.luooj.model.entity.User;

/**
* @author lenovo-mr
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-08-20 16:55:13
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest questionId
     * @param loginUser loginUser
     * @return int
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

}
