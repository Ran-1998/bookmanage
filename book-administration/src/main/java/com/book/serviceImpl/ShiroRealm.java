package com.book.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.mapper.AdminMapper;
import com.book.mapper.PermissionMapper;
import com.book.mapper.RoleMapper;
import com.book.mapper.RolePermissionMapper;
import com.book.pojo.Admin;
import com.book.pojo.Permission;
import com.book.pojo.RolePermission;

/**
 * @author Ran
 * @date 2020年4月3日
 */
@Service
public class ShiroRealm extends AuthorizingRealm {
	@Autowired
	AdminMapper adminMapper;

	@Autowired
	RoleMapper roleMapper;

	@Autowired
	RolePermissionMapper rolePermissionMapper;

	@Autowired
	PermissionMapper permissionMapper;
	
	// 凭证构造器 添加加密算法
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		// TODO Auto-generated method stub

		HashedCredentialsMatcher cMatcher = new HashedCredentialsMatcher();
		cMatcher.setHashAlgorithmName("MD5");
		cMatcher.setHashIterations(1);
		super.setCredentialsMatcher(cMatcher);
	}
	
	//权限
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		Admin admin=(Admin) principals.getPrimaryPrincipal();
		Long roleId = admin.getRoleId();
		QueryWrapper<RolePermission> queryWrapper=new QueryWrapper<>();
		queryWrapper.select("permission_Id").eq("role_Id", roleId);
		List<RolePermission> selectList = rolePermissionMapper.selectList(queryWrapper);
		if (selectList.size()==0) {
			 throw new AuthorizationException();
		}
		List<Long> perIds=new ArrayList<Long>();
		for (RolePermission rolePermission : selectList) {
			perIds.add(rolePermission.getPermissionId());
		}
		List<Permission> selectBatchIds = permissionMapper.selectBatchIds(perIds);
		List<String> permissions=new ArrayList<>();
		for (Permission permission : selectBatchIds) {
			permissions.add(permission.getPermission());
		}
		Set<String> set=new HashSet<>();
		for (String string : permissions) {
			set.add(string);
		} 
		//System.out.println(set);
		SimpleAuthorizationInfo info=
			    new SimpleAuthorizationInfo();
			    info.setStringPermissions(set);
		return info;
	}
	
	
	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String name = usernamePasswordToken.getUsername();
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
		queryWrapper.eq("name", name);
		List<Admin> selectList = adminMapper.selectList(queryWrapper);
		//System.out.println(selectList);
		if (selectList.size() == 0) {
			throw new UnknownAccountException();
		}
		SimpleAuthenticationInfo info = null;
		for (Admin admin : selectList) {
			ByteSource credentialsSalt = ByteSource.Util.bytes(admin.getSalt());
					info=new SimpleAuthenticationInfo(admin, // principal (身份)
					admin.getPassword(), // hashedCredentials
					credentialsSalt, // credentialsSalt
					getName());//
		}
		return info;
	}

}
