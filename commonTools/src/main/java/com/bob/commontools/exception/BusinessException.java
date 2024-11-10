package com.bob.commontools.exception;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName : bizException
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/10 10:09
 * @Version : 1.0
 **/
public class BusinessException extends Exception {

    @Getter
    @Setter
    private Integer code;

    @Getter
    @Setter
    private String message;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
