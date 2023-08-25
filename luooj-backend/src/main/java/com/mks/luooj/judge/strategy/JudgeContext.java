package com.mks.luooj.judge.strategy;

import com.mks.luooj.model.dto.question.JudgeCase;
import com.mks.luooj.model.dto.questionsubmit.JudgeInfo;
import com.mks.luooj.model.entity.Question;
import com.mks.luooj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
