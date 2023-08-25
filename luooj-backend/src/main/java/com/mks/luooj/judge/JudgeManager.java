package com.mks.luooj.judge;

import com.mks.luooj.judge.strategy.DefaultJudgeStrategy;
import com.mks.luooj.judge.strategy.JavaLanguageJudgeStrategy;
import com.mks.luooj.judge.strategy.JudgeContext;
import com.mks.luooj.judge.strategy.JudgeStrategy;
import com.mks.luooj.model.dto.question.JudgeCase;
import com.mks.luooj.model.dto.questionsubmit.JudgeInfo;
import com.mks.luooj.model.entity.Question;
import com.mks.luooj.model.entity.QuestionSubmit;
import com.mks.luooj.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Service;

import java.util.List;

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
