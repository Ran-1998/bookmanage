package com.book.servicerealm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.dao.IUserDao;
import com.book.entity.User;

@Service
public class ShiroUserRealm extends AuthorizingRealm {
	
	@Autowired
	private IUserDao iUserDao;
	
	/**
	 * 设置凭证匹配器（与用户添加操作使用相同的加密算法）
	 */
	
	/**
	 * 通过此方法完成认证数据的获取及封装,系统
	 * 底层会将认证数据传递认证管理器，由认证
	 * 管理器完成认证操作。
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.获取用户名(用户页面输入)
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		String username=upToken.getUsername();
		//2.基于用户名查询用户信息
		User user = iUserDao.findUserByUserName(username);
		//System.out.println(user);
		//3.判定用户是否存在
		if(user==null)
			throw new UnknownAccountException();
		//记住：构建什么对象要看方法的返回值
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
				user,//principal (身份)
				user.getPassword(),//hashedCredentials
				getName());//realName
		//6.返回封装结果
		return info;//返回值会传递给认证管理器(后续
		//认证管理器会通过此信息完成认证操作)
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

}
