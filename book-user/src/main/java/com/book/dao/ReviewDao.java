package com.book.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.book.entity.Review;

@Mapper
public interface ReviewDao {

	/** 查询bookId总记录数 */
    //int getRowCount(@Param("hashCode")String hashCode);
	int getbookIdRowCount(@Param("bookId")Integer bookId);

	//查询评论
	List<Review> queryReview(Integer bookId, int startIndex, int pageSize);

	//点赞
	public void addHeat(Integer id);

	public Review queryHeat(Integer id);
	
}
