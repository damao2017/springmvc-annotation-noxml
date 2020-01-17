package com.ntkd.common;

public enum ResultEnum {


	TEST_ERROR("10001", "测试异常"), 
	
	ERROR_UNKNOWN("19999", "未知错误！"); 

	private String code;
	private String message;

	private ResultEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static void main(String[] args) {
		System.out.println(ResultEnum.TEST_ERROR.getMessage());
	}
	
}
