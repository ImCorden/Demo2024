package com.bob.commontools.pojo.enums;

/**
 * @author tim
 * @Description: 支付状态 枚举
 */
public enum PayState {
    /**
     * 关闭
     */
    CLOSE("CLOSE", "关闭"),
    /**
     * 开启
     */
    OPEN("OPEN", "开启");

    public final String type;
    public final String value;

    PayState(String type, String value) {
        this.type = type;
        this.value = value;
    }

}
