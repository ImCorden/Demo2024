package com.bob.commontools.pojo.enums;

/**
 * @author tim
 * @Desc: 是否 枚举
 */
public enum YesOrNo {
    /**
     * 否
     */
    NO("0", "否"),
    /**
     * 是
     */
    YES("1", "是");

    public final String type;
    public final String value;

    YesOrNo(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
