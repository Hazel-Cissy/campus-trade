package com.example.campustrade.common;

public class Result {
    private int code;
    private String msg;
    private Object data;

    private Result() {}

    public static Result ok() { return ok("操作成功", null); }
    public static Result ok(String msg) { return ok(msg, null); }
    public static Result ok(Object data) { return ok("操作成功", data); }
    public static Result ok(String msg, Object data) {
        Result r = new Result();
        r.code = 200;
        r.msg = msg;
        r.data = data;
        return r;
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.code = code;
        r.msg = msg;
        r.data = null;
        return r;
    }

    public int getCode() { return code; }
    public String getMsg() { return msg; }
    public Object getData() { return data; }
}
