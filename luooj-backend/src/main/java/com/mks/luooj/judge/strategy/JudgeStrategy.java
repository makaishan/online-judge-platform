package com.mks.luooj.judge.strategy;

import com.mks.luooj.model.dto.questionsubmit.JudgeInfo;

public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);
}
