package com.mks.luoojbackendmodel.model.codesandboxmodel.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目提交编程语言枚举
 *
 */
public enum CodeSandboxTypeEnum {

    EXAMPLE("example", "example"),
    REMOTE("remote", "remote"),
    THIRD_PARTY("thirdParty", "thirdParty");

    private final String text;

    private final String value;

    CodeSandboxTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return List<String>
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value value
     * @return QuestionSubmitLanguageEnum
     */
    public static CodeSandboxTypeEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (CodeSandboxTypeEnum anEnum : CodeSandboxTypeEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
