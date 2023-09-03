package com.mks.luoojbackendjudgeservice.manager;


import com.mks.luoojbackendjudgeservice.strategy.DefaultJudgeStrategy;
import com.mks.luoojbackendjudgeservice.strategy.JavaLanguageJudgeStrategy;
import com.mks.luoojbackendjudgeservice.strategy.JudgeContext;
import com.mks.luoojbackendjudgeservice.strategy.JudgeStrategy;
import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.JudgeInfo;
import com.mks.luoojbackendmodel.model.entity.QuestionSubmit;
import com.mks.luoojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Service;

/**
 * 判题管理
 */
@Service
public class JudgeManager {

    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        // 根据编程语言选择不同的策略
        if(language.equals(QuestionSubmitLanguageEnum.JAVA.getValue())) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
