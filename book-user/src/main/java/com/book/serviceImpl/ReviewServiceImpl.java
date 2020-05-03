package com.book.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.book.dao.ReviewDao;
import com.book.entity.Review;
import com.book.service.ReviewService;
import com.book.vo.PageObject;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDao reviewDao;
	
	@Override
	public PageObject<Review> queryReview(Integer bookId, Integer pageCurrent) {

		//1.对参数进行校验
        if (pageCurrent==null || pageCurrent<1)throw new IllegalArgumentException("当前页码值无效");
        //2.查询总记录数并进行校验
        int rowCount = reviewDao.getbookIdRowCount(bookId);
        int pageSize = 4;
        int startIndex = (pageCurrent-1)*pageSize;
        List<Review> records = reviewDao.queryReview(bookId,startIndex,pageSize);
		return new PageObject<Review>(pageCurrent,pageSize,rowCount,records);
	}
	
}
