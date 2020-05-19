package com.book.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.book.annotation.RequiredLog;
import com.book.mapper.BookMapper;
import com.book.mapper.ReviewMapper;
import com.book.mapper.UserMapper;
import com.book.pojo.Book;
import com.book.pojo.Review;
import com.book.pojo.User;
import com.book.service.ReviewService;
import com.book.vo.EasyUITable;
import com.book.vo.FindReviewVo;

/**
 * @author Ran
 * @date 2020年5月1日
 */
@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewMapper reviewMapper;
	@Autowired
	BookMapper bookMapper;
	@Autowired
	UserMapper userMapper;

	@RequiredLog("查询评论")
	@Override
	public EasyUITable findReview(FindReviewVo findReviewVo) {
		// TODO Auto-generated method stub
		Integer page = findReviewVo.getPage();
		Integer rows = findReviewVo.getRows();
		if (page==null && rows==null) {
			page=0;
			rows=0;
		}
		String bookName = findReviewVo.getBookName();
		String userName = findReviewVo.getUserName();
		Page<Review> tempPage = new Page<>(page, rows);
		QueryWrapper<Review> queryWrapper = new QueryWrapper<Review>();
		queryWrapper.orderByDesc("created");
		if (bookName != null && bookName != "") {
			QueryWrapper<Book> bookqueryWrapper = new QueryWrapper<Book>();
			bookqueryWrapper.like("bookName", bookName).select("id");
			List<Book> bookSelectList = bookMapper.selectList(bookqueryWrapper);
			List<Long> ids=new ArrayList<Long>();
			for (Book book : bookSelectList) {
				Long id = book.getId();
				ids.add(id);
			}
			queryWrapper.in("book_Id", ids);
		}
		if (userName != null && userName != "") {
			QueryWrapper<User> userqueryWrapper = new QueryWrapper<User>();
			userqueryWrapper.like("name", userName).select("id");
			List<User> userSelectList = userMapper.selectList(userqueryWrapper);
			List<Long> ids=new ArrayList<Long>();
			for (User user : userSelectList) {
				Long id = user.getId();
				ids.add(id);
			}
			queryWrapper.in("user_Id", ids);
		}
		IPage<Review> IPage = reviewMapper.selectPage(tempPage, queryWrapper);
		int total = (int) IPage.getTotal();
		List<Review> reviewList = IPage.getRecords();
		EasyUITable ey = new EasyUITable(total, reviewList);
		return ey;
	}

	@RequiredLog("删除评论")
	@Override
	public void deleteBook(Long[] ids) {
		// TODO Auto-generated method stub
		List<Long> idList = Arrays.asList(ids);
		reviewMapper.deleteBatchIds(idList);
	}

}
