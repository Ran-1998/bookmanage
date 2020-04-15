package com.book.service;

import java.util.Date;

import com.book.vo.EasyUITable;
import com.book.vo.FindBorrowVo;
import com.book.vo.FindUserVo;

/**
 * @author Ran
 * @date 2020年3月27日
 */
public interface BorrowService {

	/**
	 * @param findBorrowVo
	 * @return
	 */
	EasyUITable findUser(FindBorrowVo findBorrowVo);

	/**
	 * @param ids
	 */
	void deleteBorrow(Long[] ids);

	/**
	 * @param ids
	 * @param bookIds 
	 */
	void returnBook(Long[] ids, Long[] bookIds);

	/**
	 * @param ids
	 * @param day
	 * @param returnTime 
	 */
	void delayTime(Long[] ids, Long day, String[] returnTime);

}
