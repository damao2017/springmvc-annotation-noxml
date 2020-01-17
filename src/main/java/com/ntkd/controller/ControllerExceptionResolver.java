package com.ntkd.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ntkd.common.AjaxResult;
import com.ntkd.exception.UserException;

/**
 *  @description 
 *	@author NTDM
 *	@date 2020年1月14日 上午10:01:27
 *
 */
@ControllerAdvice
public class ControllerExceptionResolver {
	
	
	private static Logger logger = LoggerFactory.getLogger(ControllerExceptionResolver.class);
	
	@ExceptionHandler({UserException.class})
	@ResponseBody
	public Object userExceptionHandler(UserException e) {
		System.out.println("============user @ControllerAdvice异常处理==========");
		AjaxResult msg = new AjaxResult();
		msg.setSuccess(false);
		msg.setData(e.toString());
		return msg;
	}
	
	@ExceptionHandler
	@ResponseBody
	public Object defaultExceptionHandler(Exception e) {
		AjaxResult msg = new AjaxResult();
		msg.setSuccess(false);
		msg.setData("未知异常"+e.toString());
		logger.error("未知异常:",e);
		return msg;
	}

}
