package com.book.service;

import com.book.vo.EasyUITable;
import com.book.vo.FindReviewVo;

/**
 * @author Ran
 * @date 2020年5月1日
 */
public interface ReviewService {

	/**
	 * @param findReviewVo
	 * @return
	 */
	EasyUITable findReview(FindReviewVo findReviewVo);

	/**
	 * @param ids
	 */
	void deleteBook(Long[] ids);

}
