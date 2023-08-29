package com.mks.luooj.codesandbox.model.entity;

import lombok.Data;

/**
 * 进程执行信息
 */
@Data
public class ExecuteMessage {
    /**
     * 退出码
     */
    private Integer exitValue;
    /**
     * 正常信息
     */
    private String message;
    /**
     * 错误信息
     */
    private String errorMessage;

    private Long time;
    private Long memory;
}
