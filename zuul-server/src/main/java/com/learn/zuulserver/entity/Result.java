package com.learn.zuulserver.entity;

/**
 * 接口响应实体类
 *
 * @since 2020/7/11
 */
public class Result {
    //表示接口请求是否成功，1为成功，0为失败
    private Integer code;
    //接口请求失败时的错误码
    private String errorCode;
    //错误描述
    private String msg;
    //接口请求返回数据
    private Object data;

    public Result() {
    }

    public Result(Integer code, Object data) {
        super();
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, String errorCode, String msg) {
        super();
        this.code = code;
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public static  Result success(Object data) {
        Result result = new Result(1, data);
        return result;
    }

    public static Result error(String errorCode, String msg) {
        Result result = new Result(0, errorCode, msg);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
