package com.example.campustrade.common;

public class BizException extends RuntimeException {
    private final int code;

    public BizException(String msg) {
        super(msg);
        this.code = 400;
    }

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() { return code; }
}
