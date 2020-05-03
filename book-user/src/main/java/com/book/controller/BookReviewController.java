package com.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.book.dao.ReviewDao;
import com.book.service.ReviewService;
import com.book.vo.JsonResult;

@Controller
@RequestMapping("/review")
public class BookReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ReviewDao reviewDao;
	
	@RequestMapping("/query")
	public @ResponseBody JsonResult queryReview(@RequestParam Integer bookId,@RequestParam Integer pageCurrent) {
		//System.out.println(pageCurrent);
		JsonResult js = new JsonResult();
    	//根据parentId查询
    	js.setData(reviewService.queryReview(bookId, pageCurrent));
    	return js;
	}
	
	@RequestMapping("/doHeat")
	public @ResponseBody JsonResult addHeat(@RequestParam Integer id) {
		//System.out.println(id);
		reviewDao.addHeat(id);
		JsonResult js = new JsonResult();
		//System.out.println(reviewDao.queryHeat(id));
		js.setData(reviewDao.queryHeat(id));
		return js;
	}
	
}
