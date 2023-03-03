package com.stonewu.blog.core.entity.enums;

import java.io.Serializable;

public enum ApiResultType implements Serializable {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 未获取到授权
     */
    NO_AUTH(10001, "未获取到授权"),
    /**
     * 校验签名失败
     */
    SIGN_ERROR(10002, "校验签名失败"),
    /**
     * 校验签名失败
     */
    DECRYPT_ERROR(10003, "校验签名失败"),
    /**
     * 参数有误
     */
    PARAM_ERROR(10004, "参数有误"),
    /**
     * 参数有误
     */
    REQUEST_ERROR(10004, "请求异常"),

    /**
     * 用户名密码错误
     */
    LOGIN_FAIL(20001, "用户名密码错误"),
    /**
     * 未找到该用户
     */
    NO_USER(20002, "未找到该用户"),
    /**
     * token异常
     */
    TOKEN_ERROR(20003, "token异常"),
    /**
     * 登录已过期
     */
    LOGIN_EXPIRE(20004, "登录已过期"),
    /**
     * 账号已被禁用
     */
    USER_BANNED(20005, "账号已被禁用"),
    /**
     * 验证码不正确
     */
    VERIFICATION_CODE_ERROR(20006, "验证码不正确"),
    /**
     * 您的账号在别处有登陆，您已被强制下线
     */
    KICK_OUT(20007, "您的账号在别处有登陆，您已被强制下线"),
    /**
     * 权限不足
     */
    PERMISSION_DENIED(20008, "权限不足"),
    /**
     * 未登录
     */
    NO_LOGIN(20009, "您还没有登录哦"),
    /**
     * 请输入用户名和密码
     */
    NO_INPUT(20010, "请输入用户名和密码"),
    /**
     * 请输入用户名和密码
     */
    UPLOAD_ERROR(30001, "上传文件失败"),

    /**
     * 未知错误
     */
    UNKNOW_ERROR(500, "未知错误"),

    /**
     * 404错误
     */
    NO_FOUND(404, "您请求的资源似乎不存在哦");

    private final int code;
    private final String msg;

    private ApiResultType(int code, String msg) {
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
