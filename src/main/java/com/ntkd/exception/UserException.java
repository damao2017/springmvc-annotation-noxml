package com.ntkd.exception;

import com.ntkd.common.ResultEnum;

public class UserException extends RuntimeException {

	
	private static final long serialVersionUID = -6101306434750991787L;
	
	protected String code;
	
	public UserException(ResultEnum resultEnum){ 
		super(resultEnum.getMessage());
		this.code = resultEnum.getCode();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	@Override
	public String toString() {
		return super.toString();
	}

	public static void main(String[] args) {
		
		try {
            //可以使用模版异常
            throw new UserException(ResultEnum.TEST_ERROR);                
			
			//其他
			//int i = 1/0;

		}catch (UserException e) {
			
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
			System.out.println(e.toString());
		}
	}

}
