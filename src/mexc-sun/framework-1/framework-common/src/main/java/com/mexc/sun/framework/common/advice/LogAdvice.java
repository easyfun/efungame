package com.mexc.sun.framework.common.advice;

import javax.validation.ValidationException;

import com.alibaba.fastjson.JSON;
import com.mexc.sun.framework.common.utils.ValidateUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LogAdvice {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(LogAdvice.class);
	

	@Around(value = "execution(* com.mexc.sun.*.dubbo.*Service.*(..))")
	public Object dubboProcess(ProceedingJoinPoint point) throws ValidationException, Throwable {
		String method = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
		Object[] params = point.getArgs();
		logger.info("[{}]请求参数:{}", method, JSON.toJSONString(params));
		for (Object obj : params) {
			ValidateUtil.validate(obj);
		}
		Object returnValue = point.proceed(params);
		logger.info("[{}]返回结果:{}", method, JSON.toJSONString(returnValue));
		return returnValue;
	}
	
	private Object process(ProceedingJoinPoint point) throws ValidationException, Throwable {
		String method = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
		Object[] params = point.getArgs();
		logger.info("[{}]请求参数:{}", method, JSON.toJSONString(params));
		Object returnValue = point.proceed(params);
		logger.info("[{}]返回结果:{}", method, JSON.toJSONString(returnValue));
		return returnValue;
	}
}
