package com.mks.luoojbackendjudgeservice.strategy;


import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.JudgeInfo;
import com.mks.luoojbackendmodel.model.dto.question.JudgeCase;
import com.mks.luoojbackendmodel.model.entity.Question;
import com.mks.luoojbackendmodel.model.entity.QuestionSubmit;
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
