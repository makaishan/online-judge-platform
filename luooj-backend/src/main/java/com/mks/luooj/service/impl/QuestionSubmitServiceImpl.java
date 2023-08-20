package com.mks.luooj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mks.luooj.common.ErrorCode;
import com.mks.luooj.exception.BusinessException;
import com.mks.luooj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.mks.luooj.model.entity.*;
import com.mks.luooj.model.entity.Question;
import com.mks.luooj.model.enums.QuestionSubmitStatusEnum;
import com.mks.luooj.model.enums.QuestionSubmitLanguageEnum;
import com.mks.luooj.service.QuestionSubmitService;
import com.mks.luooj.service.QuestionService;
import com.mks.luooj.mapper.QuestionSubmitMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lenovo-mr
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2023-08-20 16:55:13
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        // 判断实体是否存在，根据类别获取实体
        Long questionId = questionSubmitAddRequest.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 校验编程语言
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        // 是否已提交
        long userId = loginUser.getId();
        // 每个用户提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "提交失败");
        }
        return questionSubmit.getId();
    }

}




