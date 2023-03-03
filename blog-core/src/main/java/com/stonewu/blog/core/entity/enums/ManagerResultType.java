package com.stonewu.blog.core.entity.enums;

import java.io.Serializable;

public enum ManagerResultType implements Serializable {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 未知错误
     */
    UNKNOW_ERROR(500, "未知错误");

    private final int code;
    private final String msg;

    private ManagerResultType(int code, String msg) {
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
