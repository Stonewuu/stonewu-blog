package com.stonewu.blog.core.entity.result;


import com.stonewu.blog.core.entity.enums.ApiResultType;

import java.io.Serializable;

public class CommonResult<T> implements Serializable {

	private static final long serialVersionUID = 5131829603595933909L;

	private Integer code;

	private String msg;

	private String subMsg;

	private T data;

	public Integer getCode() {
		return code;
	}
	
	public boolean isSuccess() {
		return code == 200;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSubMsg() {
		return subMsg;
	}

	public void setSubMsg(String subMsg) {
		this.subMsg = subMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public CommonResult() {
	}

	public CommonResult(ApiResultType result, T data) {
		this(result);
		this.data = data;
	}

	public CommonResult(ApiResultType result, String subMsg, T data) {
		this(result, data);
		this.subMsg = subMsg;
	}

	public CommonResult(ApiResultType result) {
		this(result.getCode(), result.getMsg());
	}

	public CommonResult(ApiResultType result, String subMsg) {
		this(result);
		this.subMsg = subMsg;
	}

	public CommonResult(Integer code, String msg, T data) {
		this(code, msg);
		this.data = data;
	}

	public CommonResult(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
