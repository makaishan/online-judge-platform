package com.mks.luooj.judge.codesandbox.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 执行代码请求
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {

    /**
     * 输出用例
     */
    private List<String> inputList;
    /**
     * 代码
     */
    private String code;
    /**
     * 语言
     */
    private String language;
}
