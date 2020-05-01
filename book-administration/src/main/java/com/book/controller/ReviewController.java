package com.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.service.ReviewService;
import com.book.vo.EasyUITable;
import com.book.vo.FindReviewVo;
import com.book.vo.SysResult;

/**
 * @author Ran
 * @date 2020年5月1日
 */
@RestController
@RequestMapping("/book/review")
public class ReviewController {
@Autowired
ReviewService reviewService;
	@RequestMapping("/query")
	public EasyUITable queryReview(FindReviewVo findReviewVo) {
		
		return  reviewService.findReview(findReviewVo);
	}
	
	@RequestMapping("/delete")
	public SysResult delete(Long[] ids) {
		reviewService.deleteBook(ids);
		return SysResult.success();
	}
}
