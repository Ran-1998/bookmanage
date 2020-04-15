package com.book.service;

import com.book.vo.BookVo;
import com.book.vo.EasyUITable;
import com.book.vo.FindBookVo;
/**
 * @author Ran
 * @date 2020年2月29日
 */
public interface BookService {

	EasyUITable findBookBypage(FindBookVo findBookVo);

	/**
	 * @param bookvo
	 */
	void saveBook(BookVo bookvo);

	/**
	 * @param bookvo
	 */
	void updateBook(BookVo bookvo);

	/**
	 * @param ids
	 * @param status
	 */
	void updateStatus(Long[] ids, int status);

	/**
	 * @param ids
	 */
	void deleteBook(Long[] ids);

	/**
	 * @param id
	 * @return
	 */
	String findBookName(Long id);

}
