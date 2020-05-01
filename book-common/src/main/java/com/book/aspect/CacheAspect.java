package com.book.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.book.annotation.CacheFind;
import com.book.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@Component // 将类加给容器
@Aspect
public class CacheAspect {
	// 当tomcat服务器启动时,会启动spring容器要求立即注入对象
	// 如果当前项目中可能不需要该对象时,可以配置required = false 懒加载
	@Autowired(required = false)
	private JedisCluster jedis;

	// 环绕通知 参数必须写ProceedingJoinPoint
	@Around("@annotation(cachceFind)")
	public Object around(ProceedingJoinPoint joinPoint, CacheFind cachceFind) {
		Object obj = null;
		String key = getKey(joinPoint, cachceFind);
		System.out.println(key);
		String result = jedis.get(key);
		try {
			if (StringUtils.isEmpty(result)) {
				// 第一次查询数据
				obj = joinPoint.proceed();
				// 保存在redis中
				String json = ObjectMapperUtil.toJSON(obj);
				// 判断用户是否传递超时时间
				if (cachceFind.seconds() == 0) {
					jedis.set(key, json);
				} else {
					int seconds = cachceFind.seconds();
					jedis.setex(key, seconds, json);
				}
				System.out.println("执行数据库查询");
			} else {
				// 数据不为null,将缓存的数据转换为对象
				Class<?> returnType = getType(joinPoint);
				obj = ObjectMapperUtil.toObject(result, returnType);
				System.out.println("执行AOP缓存");
			}
		} catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return obj;
	}

	/**
	 * 
	 * @param joinPoint
	 * @return 方法的返回值类型 1.利用反射方法对象获取返回值类型
	 */
	private Class<?> getType(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getReturnType();
	}

	// 判断用户是否传递了参数,如果用户传参,使用用户自己的可以
	// 如果用户没有指定参数,使用动态生成的
	private String getKey(ProceedingJoinPoint joinPoint, CacheFind cachceFind) {
		// 获取当前方法的名称 类名.方法名
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();

		String key = cachceFind.key();
		if (!StringUtils.isEmpty(key)) {// 以用户为主
			return className + "." + methodName + "::" + key;
		} else {
			// 类名.方法名::第一个参数值
			Object[] args = joinPoint.getArgs();
			String returnkey=className + "." + methodName + "::";
			for (Object object : args) {
				returnkey=returnkey+object;
			}
			System.out.println(returnkey);
			return returnkey;
		}
	}
}
