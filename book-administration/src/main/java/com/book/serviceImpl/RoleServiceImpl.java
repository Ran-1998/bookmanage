package com.book.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.annotation.RequiredLog;
import com.book.mapper.AdminMapper;
import com.book.mapper.PermissionMapper;
import com.book.mapper.RoleMapper;
import com.book.mapper.RolePermissionMapper;
import com.book.pojo.Admin;
import com.book.pojo.Permission;
import com.book.pojo.Role;
import com.book.pojo.RolePermission;
import com.book.service.RoleService;

/**
 * @author Ran
 * @date 2020年4月4日
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleMapper roleMapper;

	@Autowired
	RolePermissionMapper rolePermissionMapper;

	@Autowired
	PermissionMapper permissionMapper;
	@Autowired
	AdminMapper adminMapper;

	// static List<Long> perParentId;
	
	@Override
	public String findRoleName(Long id) {

		String roleName = roleMapper.selectById(id).getRoleName();
		return roleName;
	}
	@RequiresPermissions("sys_admin_view")
	@RequiredLog("查询系统角色")
	@Override
	public List<Role> qureyRoleByName(String name) {
		// TODO Auto-generated method stub
		QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
		if (name != null && name != "") {
			queryWrapper.like("role_Name", name);
		}
		List<Role> selectList = roleMapper.selectList(queryWrapper);
		return selectList;
	}

	@Override
	public List<Role> qureyRole() {
		// TODO Auto-generated method stub
		List<Role> select = roleMapper.selectAll();
		return select;
	}

	@RequiresPermissions("sys_admin_add")
	@RequiredLog("新增系统角色")
	@Override
	public void addRole(String name, List<Long> per) {
		// TODO Auto-generated method stub
		Role entity = new Role();
		entity.setRoleName(name).setCreated(new Date()).setUpdated(new Date());
		roleMapper.insert(entity);
		QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
		queryWrapper.select("id").eq("role_Name", name);
		List<Role> selectList = roleMapper.selectList(queryWrapper);
		// System.out.println(selectList);
		if (per.get(0) != 0) {

			QueryWrapper<Permission> queryWrapperPer = new QueryWrapper<Permission>();
			queryWrapperPer.select("id").eq("is_Parent", 1);
			List<Permission> parentPermissionids = permissionMapper.selectList(queryWrapperPer);
			List<Long> parentPermissionId = new ArrayList<>();
			for (Permission permission : parentPermissionids) {
				parentPermissionId.add(permission.getId());
			}
			for (Long long1 : parentPermissionId) {
				if (per.contains(long1)) {
					QueryWrapper<Permission> queryWrapper2 = new QueryWrapper<Permission>();
					queryWrapper2.eq("parent_Id", long1);
					List<Permission> selectList2 = permissionMapper.selectList(queryWrapper2);
					List<Long> long2 = new ArrayList<>();
					for (Permission permission : selectList2) {
						long2.add(permission.getId());
					}
					if (!per.containsAll(long2)) {
						per.addAll(long2);
					}
					per.remove(long1);
				}
			}
			// System.out.println(per);
			for (Long permissionid : per) {
				RolePermission rolePermission = new RolePermission();
				rolePermission.setPermissionId(permissionid).setRoleId(selectList.get(0).getId()).setCreated(new Date()).setUpdated(new Date());
				rolePermissionMapper.insert(rolePermission);
			}
		}
	}

	@RequiresPermissions("sys_admin_update")
	@RequiredLog("更新系统角色")
	@Override
	public void updateRole(Long id, String roleName, List<Long> per) {
		// TODO Auto-generated method stub
		Role entity = new Role();
		entity.setId(id).setRoleName(roleName).setUpdated(new Date());
		roleMapper.updateById(entity);

		if (per.get(0) != 0) {
			QueryWrapper<Permission> queryWrapperPer = new QueryWrapper<Permission>();
			queryWrapperPer.select("id").eq("is_Parent", 1);
			List<Permission> parentPermissionids = permissionMapper.selectList(queryWrapperPer);
			List<Long> parentPermissionId = new ArrayList<>();

			for (Permission permission : parentPermissionids) {
				parentPermissionId.add(permission.getId());
			}

			QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
			queryWrapper.select("permission_Id").eq("role_Id", id);
			List<RolePermission> selectList = rolePermissionMapper.selectList(queryWrapper);
			List<Long> oldPermissionIds = new ArrayList<Long>();
			for (RolePermission rolePermission : selectList) {
				oldPermissionIds.add(rolePermission.getPermissionId());
			}
			/*
			 * HashSet<Long> hs1=new HashSet<>(permissionId); HashSet<Long> hs2=new
			 * HashSet<>(per); hs2.removeAll(hs1);
			 */
			for (Long long1 : parentPermissionId) {
				if (per.contains(long1)) {
					QueryWrapper<Permission> queryWrapper2 = new QueryWrapper<Permission>();
					queryWrapper2.eq("parent_Id", long1);
					List<Permission> selectList2 = permissionMapper.selectList(queryWrapper2);
					List<Long> long2 = new ArrayList<>();
					for (Permission permission : selectList2) {
						long2.add(permission.getId());
					}

					if (!per.containsAll(long2)) {
						per.addAll(long2);
					}
					per.remove(long1);
					// System.out.println(per);
				}
			}

			for (Long oldPer : oldPermissionIds) {
				if (per.contains(oldPer)) {
					continue;
				}
				QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
				wrapper.eq("role_id", id).eq("permission_Id", oldPer);
				rolePermissionMapper.delete(wrapper);
			}

			for (Long perId : per) {
				if (oldPermissionIds.contains(perId)) {
					continue;
				}
				RolePermission rolePermission = new RolePermission();
				rolePermission.setRoleId(id).setPermissionId(perId).setCreated(new Date()).setUpdated(new Date());
				rolePermissionMapper.insert(rolePermission);
			}
		} else {
			QueryWrapper<RolePermission> wrapper = new QueryWrapper<RolePermission>();
			wrapper.eq("role_Id", id);
			rolePermissionMapper.delete(wrapper);
		}

	}

	@RequiresPermissions("sys_admin_delete")
	@RequiredLog("删除系统角色")
	@Override
	public void deleteRole(Long[] ids) {
		
		List<Long> idList = Arrays.asList(ids);
		rolePermissionMapper.deleteRoleByIds(idList);
		for (Long id : ids) {
			QueryWrapper<Admin> queryWrapper=new QueryWrapper<Admin>();
			queryWrapper.select("id").eq("role_Id", id);
			List<Admin> selectList = adminMapper.selectList(queryWrapper);
			if (selectList.size()!=0) {
				for (Admin admin : selectList) {
					adminMapper.deleteById(admin.getId());
				}
			}
			roleMapper.deleteById(id);
			}
		
	}

}
