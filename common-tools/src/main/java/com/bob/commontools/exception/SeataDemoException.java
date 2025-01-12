package com.bob.commontools.exception;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName : seataDemoException
 * @Description : TODO
 * @Author : Bob
 * @Date : 2024/11/10 10:09
 * @Version : 1.0
 **/
@Getter
@Setter
public class SeataDemoException extends Exception {

    private Integer code;

    private String message;

    public SeataDemoException() {
        super();
    }

    public SeataDemoException(String message) {
        super(message);
    }

    public SeataDemoException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
