package com.bob.commontools.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 自定义响应数据结构
 * <p>
 * 本类可提供给 H5/ios/安卓/公众号/小程序 使用
 * <p>
 * 前端接受此类数据（json object)后，可自行根据业务去实现相关功能
 * <p>
 * 200：表示成功
 * <p>
 * 500：表示错误，错误信息在msg字段中
 * <p>
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * <p>
 * 502：拦截器拦截到用户token出错
 * <p>
 * 555：异常抛出信息
 * <p>
 * 556: 用户qq校验异常
 *
 * @author tim
 */
@Getter
@Setter
@NoArgsConstructor
public class JsonResult<T> {

    /**
     * 响应业务状态
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据，使用泛型类型
     */
    private T data;

    /**
     * 静态方法：成功时返回
     */
    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<>(200, "OK", data);
    }

    /**
     * 静态方法：成功时不返回数据
     */
    public static <T> JsonResult<T> ok() {
        return new JsonResult<>(200, "OK", null);
    }

    /**
     * 静态方法：返回错误信息
     */
    public static <T> JsonResult<T> errorMsg(String msg) {
        return new JsonResult<>(500, msg, null);
    }

    /**
     * 静态方法：返回错误码和信息
     */
    public static <T> JsonResult<T> errorCodeAndMsg(Integer status, String msg) {
        return new JsonResult<>(status, msg, null);
    }

    /**
     * 静态方法：返回错误数据
     */
    public static <T> JsonResult<T> errorMap(T data) {
        return new JsonResult<>(501, "error", data);
    }

    /**
     * 静态方法：token错误
     */
    public static <T> JsonResult<T> errorTokenMsg(String msg) {
        return new JsonResult<>(502, msg, null);
    }

    /**
     * 静态方法：异常错误
     */
    public static <T> JsonResult<T> errorException(String msg) {
        return new JsonResult<>(555, msg, null);
    }

    /**
     * 构造函数：用于传入状态、消息、数据
     */
    public JsonResult(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 构造函数：用于传入数据，默认状态为200
     */
    public JsonResult(T data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    /**
     * 判断状态是否为OK
     */
    public Boolean isOK() {
        return this.status == 200;
    }
}