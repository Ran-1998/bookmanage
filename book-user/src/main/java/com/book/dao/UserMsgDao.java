package com.book.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.book.entity.Borrow;
import com.book.entity.User;

@Mapper
public interface UserMsgDao {
	
	//查询用户历史借阅
	public List<Borrow> userHistory(@Param("studentId")Integer studentId,
            						@Param("startIndex")Integer startIndex,
            						@Param("pageSize")Integer pageSize);
	
	//查询用户基本信息
	public User userMsg(@Param("phone")Long phone);
	
	//查询历史借阅总数
	public int getHistoryRowCount(@Param("studentId")Integer studentId
			);
	
	//验证用户密码
	public int verifyUser(@Param("user")String user,
						  @Param("pwd")String pwd);

	public void updatePassword(@Param("user")String user,
							   @Param("newPwd")String newPwd);

	public int bookStatus(@Param("bookId")Integer bookId);

	public void changeBookStatus(@Param("bookId")Integer bookId);

	public void insertBorrow(@Param("id")Integer id,
							 @Param("bookId")Integer bookId,
							 @Param("format")String format,
							 @Param("vdate")String vdate);


}