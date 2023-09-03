package com.mks.luoojbackendjudgeservice.strategy;


import com.mks.luoojbackendmodel.model.codesandboxmodel.entity.JudgeInfo;

public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);
}
