package com.mks.luoojbackendjudgeservice.manager;


import com.mks.luoojbackendjudgeservice.strategy.DefaultJudgeStrategy;
import com.mks.luoojbackendjudgeservice.strategy.JavaLanguageJudgeStrategy;
import com.mks.luoojbackendjudgeservice.strategy.JudgeContext;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.JudgeInfo;
import com.mks.luoojbackendmodel.model.entity.QuestionSubmit;
import com.mks.luoojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 判题管理
 */
@Service
public class JudgeManager {

    @Resource
    private DefaultJudgeStrategy defaultJudgeStrategy;
    @Resource
    private JavaLanguageJudgeStrategy javaLanguageJudgeStrategy;

    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        // 根据编程语言选择不同的策略
        if (language.equals(QuestionSubmitLanguageEnum.JAVA.getValue())) {
            return javaLanguageJudgeStrategy.doJudge(judgeContext);
        }
        return defaultJudgeStrategy.doJudge(judgeContext);
    }
}
