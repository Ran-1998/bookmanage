package com.book.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ran
 * @date 2020年4月11日
 */
@Target(ElementType.METHOD)//方法
@Retention(RetentionPolicy.RUNTIME)//运行
public @interface CacheFind {
	//1.key可以动态获取 
		//2.key也可以自己指定
		String key() default ""; 
		
		int seconds() default 0;//用户数据不超时
}
