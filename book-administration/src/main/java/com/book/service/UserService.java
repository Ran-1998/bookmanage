package com.book.service;

import com.book.pojo.User;
import com.book.vo.EasyUITable;
import com.book.vo.FindUserVo;

/**
 * @author Ran
 * @date 2020年3月21日
 */
public interface UserService {

	/**
	 * @param findUserVo
	 * @return
	 */
	EasyUITable findUser(FindUserVo findUserVo);

	/**
	 * @param ids
	 */
	void deleteUser(Long[] ids);

	/**
	 * @param user
	 */
	void updateUser(User user);

	/**
	 * @param user
	 */
	void addUser(User user);

	/**
	 * @param id
	 * @return
	 */
	String findUserName(Long id);

	/**
	 * @param id
	 * @return
	 */
	Long findPhone(Long id);

}
