package com.ntkd.common;

public class AjaxResult {
	
	private boolean success;
	private Object data;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "AjaxResult [success=" + success + ", data=" + data + "]";
	}

}
