package com.stonewu.blog.core.entity.enums;

public enum BaseResultType {
    SUCCESS(200, "成功");

    private final int code;
    private final String msg;

    private BaseResultType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
