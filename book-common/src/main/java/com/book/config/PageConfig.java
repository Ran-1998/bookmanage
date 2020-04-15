package com.book.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
/**
 * @author Ran
 * @date 2020年2月29日
 */
@Configuration
public class PageConfig {
//配置分页插件 
	@Bean
public PaginationInterceptor paginationInterceptor() {
	
	return new PaginationInterceptor();
}
}
