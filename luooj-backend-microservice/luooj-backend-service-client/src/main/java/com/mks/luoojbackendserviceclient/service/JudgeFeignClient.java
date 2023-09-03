package com.mks.luoojbackendserviceclient.service;


import com.mks.luoojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 判题服务
 */
@FeignClient(name = "luooj-backend-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {

    /**
     * 交给代码沙箱去判题
     * @param questionSubmitId 题目提交id
     * @return QuestionSubmitVO
     */
    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
