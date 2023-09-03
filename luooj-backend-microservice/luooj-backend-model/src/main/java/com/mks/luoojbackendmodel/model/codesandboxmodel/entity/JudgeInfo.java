package com.mks.luoojbackendmodel.model.codesandboxmodel.entity;

import lombok.Data;

/**
 * 判题信息
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 程序运行占用内存
     */
    private Long memory;

    /**
     * 程序运行时间
     */
    private Long time;

}
