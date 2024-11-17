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
public class JsonResult {

    /**
     * 定义jackson对象
     */
    // private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应业务状态
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Object data;

    public static JsonResult build(Integer status, String msg, Object data) {
        return new JsonResult(status, msg, data);
    }


    public static JsonResult ok(Object data) {
        return new JsonResult(data);
    }

    public static JsonResult ok() {
        return new JsonResult(null);
    }

    public static JsonResult errorMsg(String msg) {
        return new JsonResult(500, msg, null);
    }

    public static JsonResult errorCodeAndMsg(Integer status, String msg) {
        return new JsonResult(status, msg, null);
    }

    public static JsonResult errorMap(Object data) {
        return new JsonResult(501, "error", data);
    }

    public static JsonResult errorTokenMsg(String msg) {
        return new JsonResult(502, msg, null);
    }

    public static JsonResult errorException(String msg) {
        return new JsonResult(555, msg, null);
    }

    public static JsonResult errorUserQQ(String msg) {
        return new JsonResult(556, msg, null);
    }

    public JsonResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }
}