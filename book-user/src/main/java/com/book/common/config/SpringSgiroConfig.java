package com.book.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class SpringSgiroConfig {

	/** 在Shiro配置类中添加SecurityManager配置 */
	@Bean
	public SecurityManager securityManager(Realm realm) {
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		sManager.setRealm(realm);
		return sManager;
	}
	
	/** 在Shiro配置类中添加ShiroFilterFactoryBean对象的配置。通过此对象设置资源匿名访问、认证访问 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactory (SecurityManager securityManager) {
		 ShiroFilterFactoryBean sfBean = new ShiroFilterFactoryBean();
		 sfBean.setSecurityManager(securityManager);
		//设置登录url
		 sfBean.setLoginUrl("/doIndexUI");
		 //定义map指定请求过滤规则(哪些资源允许匿名访问,哪些必须认证访问)
		 LinkedHashMap<String,String> map = new LinkedHashMap<>();
		 //静态资源允许匿名访问:"anon"
		 map.put("/bower_components/**", "anon");
		 map.put("/build/**", "anon");
		 map.put("/dist/**", "anon");
		 map.put("/add_user/**","anon");
		 map.put("/plugins/**", "anon");
		 map.put("/verify/GetVerify/**", "anon");
		 map.put("/login", "anon");
		 map.put("/addUser", "anon");
		 map.put("dysmsapi.aliyuncs.com", "anon");
		 map.put("/doLogout","logout");
		 //除了匿名访问的资源,其它都要认证("authc")后访问
		 map.put("/**","authc");
		 sfBean.setFilterChainDefinitionMap(map);
		 return sfBean;
	}
	
	//添加授权时的相关配置：
	/**第一步:配置bean对象的生命周期管理。 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	/** 第二步: 通过如下配置要为目标业务对象创建代理对象（SpringBoot中可省略） */
	@DependsOn("lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
	}
	/** 第三步:配置advisor对象,shiro框架底层会通过此对象的matchs方法返回值决定是否创建代理对象,进行权限控制。 */
	@Bean
	public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(@Autowired SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}



}
