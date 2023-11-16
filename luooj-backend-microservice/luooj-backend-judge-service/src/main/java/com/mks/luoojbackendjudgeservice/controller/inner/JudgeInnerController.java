package com.mks.luoojbackendjudgeservice.controller.inner;

import com.mks.luoojbackendjudgeservice.service.JudgeService;
import com.mks.luoojbackendmodel.model.entity.QuestionSubmit;
import com.mks.luoojbackendserviceclient.service.JudgeFeignClient;
import com.mks.luoojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Override
    @PostMapping("/do")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        System.out.println(questionFeignClient);
        return judgeService.doJudge(questionSubmitId);
    }
}
