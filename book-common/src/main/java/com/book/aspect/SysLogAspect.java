package com.book.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.book.annotation.RequiredLog;
import com.book.pojo.SysLog;
import com.book.service.LogService;
import com.book.util.IPUtils;
import com.book.util.ShiroUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ran
 * @date 2020年3月29日
 */
//优先级
@Order(1)
@Slf4j
@Aspect
@Component
public class SysLogAspect {

	@Autowired
	LogService logService;
	// 切入点
	@Pointcut("@annotation(com.book.annotation.RequiredLog)")
	public void logPointCut() {
	}

	// 环绕通知
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

		// 1.记录目标方法开始执行时间
		long t1 = System.currentTimeMillis();
		log.info("start time:" + t1);
		// 2.执行目标方法
		Object result = joinPoint.proceed();// 假如有下一个切面先执行切面对象方法
		// 3.记录目标方法结束执行时间
		long t2 = System.currentTimeMillis();
		log.info("end time:" + t2);
		// 记录用户行为日志
		saveLog(joinPoint, (t2 - t1));
		return result;
	}

	/**
	 * @param joinPoint
	 * @param time
	 * @throws NoSuchMethodException
	 * @throws JsonProcessingException
	 */
	private void saveLog(ProceedingJoinPoint joinPoint, long time) throws NoSuchMethodException, JsonProcessingException {
		// TODO Auto-generated method stub
		// 1.获取日志信息
		// 获取目标方法对象
		Method targetMethod = getTargetMethod(joinPoint);
		// 获取目标方法名:目标类全名+方法名
		String classMethodName = getTargetMethodName(targetMethod);
		// 获取操作名
		String operation = getOperation(targetMethod);
		// 获取方法执行时的实际参数
		String params = new ObjectMapper().writeValueAsString(joinPoint.getArgs());
		// 2.封装日志信息
		/*
		 * sysLog = new SysLog(null, //ShiroUtils.getUserName() null, // 登录用户名
		 * operation, classMethodName, params, time, IPUtils.getIpAddr(), new Date(),new
		 * Date());
		 */
		SysLog sysLog=new SysLog();
		sysLog.setOperation(operation).setMethod(classMethodName).setParams(params).setTime(time).setIp(IPUtils.getIpAddr()).setUsername(ShiroUtils.getUserName()).setCreated(new Date()).setUpdated(new Date());
		// 3.持久化日志信息
		logService.saveLog(sysLog);;
	}

	private String getOperation(Method targetMethod) {
		RequiredLog rLog = targetMethod.getAnnotation(RequiredLog.class);
		return rLog.value();
	}

	private String getTargetMethodName(Method targetMethod) {
		return targetMethod.getDeclaringClass().getName() + "." + targetMethod.getName();
	}

	private Method getTargetMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
		Class<?> targetClass = joinPoint.getTarget().getClass();
		MethodSignature s = (MethodSignature) joinPoint.getSignature();// 方法签名
		Method targetMethod = targetClass.getMethod(s.getName(), s.getParameterTypes());
		return targetMethod;
	}
}
