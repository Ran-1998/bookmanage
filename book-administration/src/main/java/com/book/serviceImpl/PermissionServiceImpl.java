package com.book.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.annotation.RequiredLog;
import com.book.mapper.PermissionMapper;
import com.book.mapper.RolePermissionMapper;
import com.book.pojo.Permission;
import com.book.pojo.RolePermission;
import com.book.service.PermissionService;
import com.book.vo.EasyUICatTree;
import com.book.vo.EasyUIPermissionTree;
import com.book.vo.EasyUIRoleTree;
import com.book.vo.EasyUITree;
import com.book.vo.SysResult;

/**
 * @author Ran
 * @date 2020年4月4日
 */
@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	PermissionMapper permissionMapper;
	@Autowired
	RolePermissionMapper rolePermissionMapper;

	static List<Long> roleIds;

	static Long parentId;
	@RequiresPermissions("sys_admin_view")
	@Override
	public List<Permission> findPerByRole(Integer id) {
		// TODO Auto-generated method stub

		QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<RolePermission>();
		queryWrapper.eq("role_id", id);
		List<RolePermission> selectList = rolePermissionMapper.selectList(queryWrapper);
		// System.out.println(selectList);
		List<Permission> rt = new ArrayList<Permission>();
		for (RolePermission rolePermission : selectList) {
			Long permissionId = rolePermission.getPermissionId();
			Permission selectById = permissionMapper.selectById(permissionId);
			rt.add(selectById);
		}
		return rt;
	}

	@Override
	public List<EasyUIRoleTree> findPermissionTree(Long parentId) {
		// TODO Auto-generated method stub
		// System.out.println(roleIds);
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
		queryWrapper.eq("parent_id", parentId);
		List<Permission> selectList = permissionMapper.selectList(queryWrapper);
		List<EasyUIRoleTree> easyUIRoleTrees = new ArrayList<EasyUIRoleTree>(selectList.size());
		for (Permission permission : selectList) {
			Long id = permission.getId();
			String text = permission.getPermissionName();
			String state = permission.getIsParent() ? "closed" : "open";
			EasyUIRoleTree easyUIRoleTree = new EasyUIRoleTree();
			easyUIRoleTree.setId(id).setText(text).setState(state);
			easyUIRoleTrees.add(easyUIRoleTree);
		}

		return easyUIRoleTrees;
	}

	@Override
	public void setRoleId(Long Id) {
		// TODO Auto-generated method stub

		roleIds = new ArrayList<Long>();
		QueryWrapper<RolePermission> queryWrapper1 = new QueryWrapper<RolePermission>();
		queryWrapper1.select("permission_Id").eq("role_id", Id);
		List<RolePermission> selectList2 = rolePermissionMapper.selectList(queryWrapper1);
		// System.out.println(selectList2);
		for (RolePermission rolePermission : selectList2) {
			Long permissionId = rolePermission.getPermissionId();
			roleIds.add(permissionId);
		}
	}

	@Override
	public List<EasyUIRoleTree> findPermissionTrees() {
		// TODO Auto-generated method stub

		List<Permission> selectList = select(0l);
		List<EasyUIRoleTree> easyUIRoleTrees = new ArrayList<EasyUIRoleTree>(selectList.size());
		for (Permission permission : selectList) {
			Long id = permission.getId();
			EasyUIRoleTree easyUIRoleTree = getEasyUIRoleTree(permission);
			if (permission.getIsParent()) {
				List<EasyUIRoleTree> easyUIRoleTrees2 = new ArrayList<EasyUIRoleTree>(selectList.size());
				List<Permission> select2 = select(id);
				for (Permission permission2 : select2) {
					Long id2 = permission2.getId();
					EasyUIRoleTree easyUIRoleTree2 = getEasyUIRoleTree(permission2);
					if (permission2.getIsParent()) {
						List<EasyUIRoleTree> easyUIRoleTrees3 = new ArrayList<EasyUIRoleTree>(selectList.size());
						List<Permission> select3 = select(id2);
						for (Permission permission3 : select3) {
							EasyUIRoleTree easyUIRoleTree3 = getEasyUIRoleTree(permission3);
							easyUIRoleTrees3.add(easyUIRoleTree3);
							easyUIRoleTree2.setChildren(easyUIRoleTrees3);
						}
					}
					easyUIRoleTrees2.add(easyUIRoleTree2);
					easyUIRoleTree.setChildren(easyUIRoleTrees2);
				}
			}
			easyUIRoleTrees.add(easyUIRoleTree);
		}
		return easyUIRoleTrees;
	}

	public List<Permission> select(Long id) {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
		queryWrapper.eq("parent_id", id);
		List<Permission> selectList = permissionMapper.selectList(queryWrapper);
		return selectList;
	}

	public EasyUIRoleTree getEasyUIRoleTree(Permission permission) {
		EasyUIRoleTree easyUIRoleTree = new EasyUIRoleTree();
		Long id = permission.getId();
		String text = permission.getPermissionName();
		String state = permission.getIsParent() ? "closed" : "open";
		easyUIRoleTree.setId(id).setText(text).setState(state);
		if (roleIds != null) {
			easyUIRoleTree.setChecked(roleIds.contains(id));
		}
		return easyUIRoleTree;
	}
	@RequiresPermissions("sys_admin_view")
	@RequiredLog("查询系统权限")
	@Override
	public List<EasyUIPermissionTree> findAll(Long parentId) {
		// TODO Auto-generated method stub
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
		queryWrapper.eq("parent_id", parentId);
		List<Permission> selectList = permissionMapper.selectList(queryWrapper);
		List<EasyUIPermissionTree> perTrees = new ArrayList<>();
		for (Permission permission : selectList) {
			EasyUIPermissionTree easyUIPermissionTree = new EasyUIPermissionTree();
			String state = permission.getIsParent() ? "closed" : "open";
			easyUIPermissionTree.setState(state).setId(permission.getId()).setIsParent(permission.getIsParent()).setPermissionName(permission.getPermissionName()).setParentId(permission.getParentId())
					.setPermission(permission.getPermission()).setCreated(permission.getCreated()).setUpdated(permission.getUpdated());
			perTrees.add(easyUIPermissionTree);
		}
		return perTrees;
	}

	@Override
	public String findPerName(Long id) {
		// TODO Auto-generated method stub
		return permissionMapper.selectById(id).getPermissionName();
	}

	@Override
	public List<EasyUITree> listPer(Long parentId) {
		// TODO Auto-generated method stub
		List<Permission> perList = findPerByParentId(parentId);
		List<EasyUITree> treeList = new ArrayList<EasyUITree>(perList.size());
		for (Permission permission : perList) {
			Long id = permission.getId();
			String text = permission.getPermissionName();
			String state = permission.getIsParent() ? "closed" : "open";
			EasyUITree tree = new EasyUITree();
			tree.setId(id).setText(text).setState(state);
			treeList.add(tree);
		}
		return treeList;
	}

	private List<Permission> findPerByParentId(Long parentId) {
		// TODO Auto-generated method stub
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
		queryWrapper.eq("parent_id", parentId);
		List<Permission> catList = permissionMapper.selectList(queryWrapper);
		return catList;
	}

	@Override
	public SysResult checkLV(Long parentId) {
		// TODO Auto-generated method stub
		SysResult result = new SysResult();
		Permission parentCat = permissionMapper.selectById(parentId);
		if (parentCat != null) {

			// System.out.println(parentCat);
			Permission parentCat2 = permissionMapper.selectById(parentCat.getParentId());
			// System.out.println(parentCat2);
			if (parentCat2 != null) {

				if (parentCat2.getParentId() != null && parentCat2.getParentId() != 0) {
					result.setMsg("最多三级权限");
					return result;
				} else {
					return SysResult.success();
				}
			}
		}

		return SysResult.success();

	}
	
	@RequiresPermissions("sys_admin_add")
	@RequiredLog("新增系统权限")
	@Override
	public void addPer(Permission per) {
		// TODO Auto-generated method stub
		per.setCreated(new Date()).setUpdated(new Date());
		Long parentId = per.getParentId();
		if (parentId == null) {
			parentId = 0l;
			per.setParentId(parentId);
		} else if (parentId != 0) {
			Permission selectById = permissionMapper.selectById(parentId);
			if (!selectById.getIsParent()) {
				selectById.setIsParent(true);
				permissionMapper.updateById(selectById);
			}

		}

		if (per.getIsParent() == null) {
			per.setIsParent(false);
		}
		permissionMapper.insert(per);

	}
	@RequiresPermissions("sys_admin_delete")
	@RequiredLog("删除系统权限")
	@Override
	public void deletePer(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			Permission selectById = permissionMapper.selectById(id);
			if (selectById != null) {
				Boolean isParent = selectById.getIsParent();
				Long parentId = selectById.getParentId();
				if (isParent) {
					// 2
					if (parentId != 0) {
						deleteRolePermisson(id);
						permissionMapper.deleteById(id);
						deleteNext(id);
						changeParent(parentId);
					} else {
						deleteRolePermisson(id);
						permissionMapper.deleteById(id);
						List<Permission> deleteNext = deleteNext(id);
						if (deleteNext != null) {
							for (Permission permission : deleteNext) {
								deleteNext(permission.getId());
							}
						}
					}
				} else {
					deleteRolePermisson(id);
					permissionMapper.deleteById(id);
					changeParent(parentId);
				}
			}
		}
	}
	/**
	 * @param id
	 */
	private void deleteRolePermisson(Long id) {
		// TODO Auto-generated method stub
		QueryWrapper<RolePermission> wrapper=new QueryWrapper<>();
		wrapper.eq("permission_Id", id);
		rolePermissionMapper.delete(wrapper);
	}

	@RequiresPermissions("sys_admin_update")
	@RequiredLog("更新系统权限")
	@Override
	public void updateper(Permission permission) {
		// TODO Auto-generated method stub
		Long newParentId = permission.getParentId();
		Long id = permission.getId();
		Permission selectById = permissionMapper.selectById(id);
		Long oldParentId = selectById.getParentId();
		permission.setUpdated(new Date());
		permissionMapper.updateById(permission);

		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("id").eq("parent_Id", oldParentId);
		List<Permission> selectList = permissionMapper.selectList(queryWrapper);
		if (selectList.size() == 0) {
			Permission permission2=new Permission();
			permission2.setId(oldParentId).setIsParent(false).setUpdated(new Date());
			permissionMapper.updateById(permission2);
		}
		Permission selectById2 = permissionMapper.selectById(newParentId);
		if (selectById2!=null && !selectById2.getIsParent()) {
			Permission permission2=new Permission();
			permission2.setId(newParentId).setIsParent(true).setUpdated(new Date());
			permissionMapper.updateById(permission2);
		}
	}

	public void changeParent(Long parentId) {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_Id", parentId);
		List<Permission> selectList = permissionMapper.selectList(queryWrapper);
		if (selectList.size() == 0) {
			permissionMapper.updateById(new Permission().setId(parentId).setIsParent(false));
		}
	}

	public List<Permission> deleteNext(Long id) {
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("id").eq("parent_Id", id);
		List<Permission> selectList = permissionMapper.selectList(queryWrapper);
		if (selectList.size() != 0) {
			for (Permission permission : selectList) {
				deleteRolePermisson(permission.getId());
				permissionMapper.deleteById(permission.getId());
			}
			return selectList;
		}
		return null;
	}

	@Override
	public void setparent(Long parent) {
		// TODO Auto-generated method stub
		System.out.println(parent);
		parentId = parent;
	}

	@Override
	public Long getparent() {
		// TODO Auto-generated method stub
		System.out.println(parentId);
		return parentId;
	}
	
	@RequiresPermissions("sys_admin_view")
	@RequiredLog("查询系统权限")
	@Override
	public List<EasyUIPermissionTree> select(String name) {
		// TODO Auto-generated method stub
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
		queryWrapper.like("permission_Name", name);
		List<Permission> selectList = permissionMapper.selectList(queryWrapper);
		List<EasyUIPermissionTree> perTrees = new ArrayList<>();
		for (Permission permission : selectList) {
			EasyUIPermissionTree easyUIPermissionTree = new EasyUIPermissionTree();
			String state = permission.getIsParent() ? "closed" : "open";
			easyUIPermissionTree.setState(state).setId(permission.getId()).setIsParent(permission.getIsParent()).setPermissionName(permission.getPermissionName()).setParentId(permission.getParentId())
					.setPermission(permission.getPermission()).setCreated(permission.getCreated()).setUpdated(permission.getUpdated());
			perTrees.add(easyUIPermissionTree);
		}
		return perTrees;
	}

}
