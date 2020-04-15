package com.book.config;

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

/**
 * @author Ran
 * @date 2020年4月3日
 */
@Configuration
public class ShiroConfig {

	@Bean
	public SecurityManager securityManager(Realm realm) {
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		sManager.setRealm(realm);
		return sManager;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager) {
		ShiroFilterFactoryBean sfBean = new ShiroFilterFactoryBean();
		sfBean.setSecurityManager(securityManager);
		sfBean.setLoginUrl("/admin/doLoginUI");
		// 过滤规则
		LinkedHashMap<String, String> shiroMap = new LinkedHashMap<>();

		shiroMap.put("/commons/**", "anon");
		shiroMap.put("/css/**", "anon");
		shiroMap.put("/easy-ui/**", "anon");
		shiroMap.put("/js/**", "anon");
		shiroMap.put("/admin/login","anon");
		shiroMap.put("/admin/loginOut","logout");
		shiroMap.put("/**", "authc");

		sfBean.setFilterChainDefinitionMap(shiroMap);
		return sfBean;
	}
	//授权配置
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
			 return new LifecycleBeanPostProcessor();
	}
	
	@DependsOn("lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
			 return new DefaultAdvisorAutoProxyCreator();
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor 
	newAuthorizationAttributeSourceAdvisor(
		    		    @Autowired SecurityManager securityManager) {
			        AuthorizationAttributeSourceAdvisor advisor=
					new AuthorizationAttributeSourceAdvisor();
	advisor.setSecurityManager(securityManager);
		return advisor;
	}
}
