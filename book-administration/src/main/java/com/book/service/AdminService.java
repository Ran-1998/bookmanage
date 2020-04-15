package com.book.service;

import com.book.pojo.Admin;
import com.book.vo.AdminVo;
import com.book.vo.EasyUITable;

/**
 * @author Ran
 * @date 2020年3月29日
 */
public interface AdminService {

	/**
	 * @param admin
	 */
	void save(Admin admin);

	/**
	 * @param adminVo
	 * @return
	 */
	EasyUITable query(AdminVo adminVo);

	/**
	 * @param admin
	 * @return
	 */
	void upadte(Admin admin);

	/**
	 * @param ids
	 */
	void delete(Long[] ids);
}
