package com.stonewu.blog.core.entity.result;

import com.stonewu.blog.core.entity.enums.ApiResultType;

import java.util.Map;

public class MapObjectResult extends CommonResult<Map<String, Object>> {

	private static final long serialVersionUID = -87749144248269845L;

	public MapObjectResult() {
	}

	public MapObjectResult(ApiResultType result) {
		super(result);
	}

	public MapObjectResult(ApiResultType result, Map<String, Object> data) {
		super(result, data);
	}

	public MapObjectResult(Integer code, String msg, Map<String, Object> data) {
		super(code, msg, data);
	}

	public MapObjectResult(Integer code, String msg) {
		super(code, msg);
	}

	public MapObjectResult(ApiResultType result, String subMsg, Map<String, Object> data) {
		super(result, subMsg, data);
	}

	public MapObjectResult(ApiResultType result, String subMsg) {
		super(result, subMsg);
	}

}
