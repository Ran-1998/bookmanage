package com.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.pojo.Permission;
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
@RestController
@RequestMapping("/permission")
public class PermissonCotroller {
	@Autowired
	PermissionService permissionService;

	@RequestMapping("findPerByRole")
	public List<Permission> findPerByRole(Integer id) {
		return permissionService.findPerByRole(id);
	}

	@RequestMapping("findPermissionTree")
	public List<EasyUIRoleTree> findPermissionTree(@RequestParam(value = "id", defaultValue = "0") Long id) {
		return permissionService.findPermissionTree(id);
	}

	@RequestMapping("findPermissionTrees")
	public List<EasyUIRoleTree> findPermissionTrees() {
		return permissionService.findPermissionTrees();
	}

	@RequestMapping("setRoleId")
	public void setRoleId(Long roleId) {
		permissionService.setRoleId(roleId);
	}

	@RequestMapping("/listAll")
	public List<EasyUIPermissionTree> findAll(@RequestParam(value = "id", defaultValue = "0") Long parentId) {

		return permissionService.findAll(parentId);

	}

	@RequestMapping("findPerName")
	public String findPerName(Long id) {
		if (id != 0 && id != null) {
			return permissionService.findPerName(id);
		} else {
			return "无";
		}
	}

	@RequestMapping("list")
	public List<EasyUITree> listPer(@RequestParam(value = "id", defaultValue = "0") Long id) {
		return permissionService.listPer(id);
	}

	@RequestMapping("/checkLV")
	public SysResult checkLv(Long parentId) {
		return permissionService.checkLV(parentId);
	}

	@RequestMapping("/add")
	public SysResult addPer(Permission permission) {
		//System.out.println(permission);
		permissionService.addPer(permission);
		return SysResult.success();
	}
	
	@RequestMapping("/delete")
	public SysResult deletePer(Long[] ids) {
		
		permissionService.deletePer(ids);
		return SysResult.success();
				
	}
	
	@RequestMapping("/setparent")
	public void setparent(Long parent) {
		permissionService.setparent(parent);
	}
	
	@RequestMapping("/getparent")
	public Long getparent() {
		return permissionService.getparent();
	}
	
	@RequestMapping("/update")
	public SysResult update(Permission permission) {
		//System.out.println(permission);
		permissionService.updateper(permission);
		return SysResult.success();
	}
	
	@RequestMapping("/select")
	 public List<EasyUIPermissionTree> select(String name) {
		return permissionService.select(name);
		
	}
}
