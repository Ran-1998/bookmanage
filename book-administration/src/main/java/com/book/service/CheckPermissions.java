package com.book.service;

/**
 * @author Ran
 * @date 2020年5月21日
 */
public interface CheckPermissions {

	/**
	 * 
	 */
	void checkBookQuery();

	/**
	 * 
	 */
	void checkBookAdd();

	/**
	 * 
	 */
	void checkBookDelete();

	/**
	 * 
	 */
	void checkBookUpadte();

	/**
	 * 
	 */
	void checkUserUpdate();

	/**
	 * 
	 */
	void checkUserQuery();

	/**
	 * 
	 */
	void checkUserDelete();

	/**
	 * 
	 */
	void checkUserAdd();

	/**
	 * 
	 */
	void checkAdminAdd();

	/**
	 * 
	 */
	void checkAdminDelete();

	/**
	 * 
	 */
	void checkAdminUpdate();

	/**
	 * 
	 */
	void checkAdminQuery();

}
