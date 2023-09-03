package com.mks.luoojbackendmodel.model.dto.questionsubmit;

import lombok.Data;

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
