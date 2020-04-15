package com.book.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.pojo.Role;
import com.book.service.RoleService;
import com.book.vo.EasyUITree;
import com.book.vo.SysResult;

/**
 * @author Ran
 * @date 2020年4月4日
 */
@RestController
@RequestMapping("/role")
public class RoleController {
@Autowired
private RoleService roleService;

@RequestMapping("findRoleName")
public String findRoleName(Long id) {
	return roleService.findRoleName(id);
}

@RequestMapping("qurey")
public List<Role> qureyRole(String name){
	return roleService.qureyRoleByName(name);
}
@RequestMapping("listAll")
public List<Role> list(){
	return roleService.qureyRole();
}
@RequestMapping("add")
public SysResult add(String name,String perids){
	String[] split = perids.split(",");
	List<Long> per=new ArrayList<Long>();
	for (String string : split) {
		if (!string.equals("")) {
			long parseLong = Long.parseLong(string);
			per.add(parseLong);
		}else {per.add(0l);}
	}
	roleService.addRole(name,per);
	return SysResult.success();
}

@RequestMapping("update")
public SysResult updateRole(Long id,String roleName,String perids) {
	String[] split = perids.split(",");
	List<Long> per=new ArrayList<Long>();
	for (String string : split) {
		if (!string.equals("")) {
			long parseLong = Long.parseLong(string);
			per.add(parseLong);
		}else {per.add(0l);}
	}
	roleService.updateRole(id,roleName,per);
	return SysResult.success();
}

@RequestMapping("delete")
public SysResult deleteRole(Long[] ids) {
	
	roleService.deleteRole(ids);
	return SysResult.success();
}

}
