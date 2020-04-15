package com.book.service;

import java.util.List;

import com.book.pojo.Role;

/**
 * @author Ran
 * @date 2020年4月4日
 */
public interface RoleService {

	/**
	 * @param id
	 * @return
	 */
	String findRoleName(Long id);

	/**
	 * @return
	 */
	List<Role> qureyRole();

	/**
	 * @param id
	 * @param roleName
	 * @param per
	 */
	void updateRole(Long id, String roleName, List<Long> per);

	/**
	 * @param name
	 * @param per
	 * @return
	 */
	void addRole(String name, List<Long> per);

	/**
	 * @param ids
	 */
	void deleteRole(Long[] ids);

	/**
	 * @param name
	 * @return
	 */
	List<Role> qureyRoleByName(String name);

}
