package com.stonewu.blog.core.entity.result;


import com.stonewu.blog.core.entity.enums.ApiResultType;

public class ObjectResult extends CommonResult<Object> {

	private static final long serialVersionUID = 2602486984901223568L;

	public ObjectResult() {

	}

	public ObjectResult(ApiResultType result) {
		super(result);
	}

	public ObjectResult(ApiResultType result, Object data) {
		super(result, data);
	}

	public ObjectResult(Integer code, String msg, Object data) {
		super(code, msg, data);
	}

	public ObjectResult(Integer code, String msg) {
		super(code, msg);
	}

	public ObjectResult(ApiResultType result, String subMsg, Object data) {
		super(result, subMsg, data);
	}

	public ObjectResult(ApiResultType result, String subMsg) {
		super(result, subMsg);
	}

}
