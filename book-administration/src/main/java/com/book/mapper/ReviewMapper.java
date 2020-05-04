package com.book.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.pojo.Review;

/**
 * @author Ran
 * @date 2020年5月1日
 */
@Mapper
public interface ReviewMapper extends BaseMapper<Review>{

	public void deleteBookByIds(@Param("idList") List<Long> idList);
	public void deleteUserByIds(@Param("idList") List<Long> idList);
}
