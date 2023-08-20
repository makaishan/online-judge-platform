package com.mks.luooj.model.dto.questionsubmit;

import lombok.Data;

import java.util.Date;

@Data
public class QuestionSubmitAddRequest {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交代码
     */
    private String code;


    /**
     * 题目 id
     */
    private Long questionId;

}
