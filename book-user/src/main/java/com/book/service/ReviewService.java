package com.book.service;

import com.book.entity.Review;
import com.book.vo.PageObject;

public interface ReviewService {

	PageObject<Review> queryReview(Integer bookId, Integer pageCurrent);
	
}
