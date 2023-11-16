package com.mks.luoojbackendjudgeservice.strategy;


import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.JudgeInfo;
import com.mks.luoojbackendmodel.model.entity.Question;

public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);

    boolean updateAcceptedRate(Question question, boolean isAccepted);
}
