package com.book.service;

import java.util.List;

import com.book.pojo.Permission;
import com.book.vo.EasyUICatTree;
import com.book.vo.EasyUIPermissionTree;
import com.book.vo.EasyUIRoleTree;
import com.book.vo.EasyUITable;
import com.book.vo.EasyUITree;
import com.book.vo.PermissionVo;
import com.book.vo.SysResult;

/**
 * @author Ran
 * @date 2020年4月4日
 */
public interface PermissionService {

	/**
	 * @param id 
	 * @return
	 */
	List<Permission> findPerByRole(Integer id);

	/**
	 * @param id
	 * @return
	 */
	List<EasyUIRoleTree> findPermissionTree(Long id);

	/**
	 * @param roleId
	 * @return
	 */
	void setRoleId(Long roleId);

	/**
	 * @return
	 */
	List<EasyUIRoleTree> findPermissionTrees();

	/**
	 * @param parentId
	 * @return
	 */
	List<EasyUIPermissionTree> findAll(Long parentId);

	/**
	 * @param id
	 * @return
	 */
	String findPerName(Long id);

	/**
	 * @param id
	 * @return
	 */
	List<EasyUITree> listPer(Long id);

	/**
	 * @param parentId
	 * @return
	 */
	SysResult checkLV(Long parentId);

	/**
	 * @param permission
	 */
	void addPer(Permission permission);

	/**
	 * @param ids
	 */
	void deletePer(Long[] ids);

	/**
	 * @param parent
	 */
	void setparent(Long parent);

	/**
	 * @return
	 */
	Long getparent();

	/**
	 * @param permission
	 */
	void updateper(Permission permission);

	/**
	 * @param name
	 * @return
	 */
	List<EasyUIPermissionTree> select(String name);

}
