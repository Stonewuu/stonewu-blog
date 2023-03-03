package com.stonewu.blog.core.entity.result;


import com.stonewu.blog.core.entity.enums.ApiResultType;

import java.util.Map;

public class MapStringResult extends CommonResult<Map<String, String>> {

	private static final long serialVersionUID = -87749144248269845L;

	public MapStringResult() {
	}

	public MapStringResult(ApiResultType result) {
		super(result);
	}

	public MapStringResult(ApiResultType result, Map<String, String> data) {
		super(result, data);
	}

	public MapStringResult(Integer code, String msg, Map<String, String> data) {
		super(code, msg, data);
	}

	public MapStringResult(Integer code, String msg) {
		super(code, msg);
	}

	public MapStringResult(ApiResultType result, String subMsg, Map<String, String> data) {
		super(result, subMsg, data);
	}

	public MapStringResult(ApiResultType result, String subMsg) {
		super(result, subMsg);
	}

}
