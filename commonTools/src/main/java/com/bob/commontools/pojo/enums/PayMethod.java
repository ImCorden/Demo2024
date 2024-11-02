package com.bob.commontools.pojo.enums;

/**
 * @author tim
 * @Description: 支付方式 枚举
 */
public enum PayMethod {
    /**
     * 微信
     */
    WECHAT(1, "微信"),
    /**
     * 支付宝
     */
    ALIPAY(2, "支付宝");

    public final Integer type;
    public final String value;

    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

}
