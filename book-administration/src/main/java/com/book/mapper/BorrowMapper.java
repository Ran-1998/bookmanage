package com.book.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.pojo.Borrow;

/**
 * @author Ran
 * @date 2020年3月27日
 */
@Mapper
public interface BorrowMapper extends BaseMapper<Borrow>{
	
	@Select("select * from user_borrow where book_Id = #{bookId}")
	List<Borrow> selectByBookId(@Param("bookId")Long id);

	/**
	 * @param id
	 * @return
	 */
	@Select("select * from user_borrow where user_Id = #{userId}")
	List<Borrow> selectByUserId(@Param("userId") Long id);
	
	public void deleteBookByIds(@Param("idList") List<Long> idList);
	public void deleteUserByIds(@Param("idList") List<Long> idList);
	
}
