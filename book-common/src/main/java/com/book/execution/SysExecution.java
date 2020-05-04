package com.book.execution;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alibaba.druid.util.StringUtils;
import com.book.vo.SysResult;
import com.fasterxml.jackson.databind.util.JSONPObject;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ran
 * @date 2020年2月29日
 */
@RestControllerAdvice // 异常通知 对controller层生效
@Slf4j
public class SysExecution {

	
	@ExceptionHandler( ShiroException.class )
	public Object shiroError( ShiroException shiroException) {
		SysResult r = new SysResult();
		if (shiroException instanceof UnknownAccountException) {
			r.setMsg("账户不存在").setStatus(201);
			return r;
		} else if (shiroException instanceof IncorrectCredentialsException) {
			r.setMsg("密码不正确").setStatus(201);
			return r;
		} else if (shiroException instanceof AuthorizationException) {
			r.setMsg("没有此操作权限").setStatus(201);
			return r;
		}  else {
			r.setMsg("系统维护中").setStatus(201);
			return r;
		}

	}

	@ExceptionHandler(RuntimeException.class)
	public Object error(Exception exception, HttpServletRequest request) {
		String callback = request.getParameter("callback");
		if (StringUtils.isEmpty(callback)) {

			exception.printStackTrace();
			log.error(exception.getMessage());
			return SysResult.fail();
		} else {
			exception.printStackTrace();
			log.error(exception.getMessage());
			return new JSONPObject(callback, SysResult.fail());
		}
	}

}
