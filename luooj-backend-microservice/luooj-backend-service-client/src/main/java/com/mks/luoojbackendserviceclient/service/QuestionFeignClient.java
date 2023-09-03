package com.mks.luoojbackendserviceclient.service;

import com.mks.luoojbackendmodel.model.entity.Question;
import com.mks.luoojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
* @author lenovo-mr
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-08-20 16:55:13
*/
@FeignClient(name = "luooj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    /**
     * 根据题目id获取题目
     * @param questionId questionId
     * @return Question
     */
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    /**
     * 根据题目提交id获取题目提交
     * @param questionSubmitId questionSubmitId
     * @return QuestionSubmit
     */
    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);

    /**
     * 更新题目提交信息
     * @param questionSubmit questionSubmit
     * @return boolean
     */
    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);

}
