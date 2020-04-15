package com.book.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.pojo.BookCategory;
/**
 * @author Ran
 * @date 2020年2月29日
 */
@Mapper
public interface CategoryMapper extends BaseMapper<BookCategory>{
	@Insert("insert into book_category values(null,#{parentId},#{name},#{status},#{isParent},#{created},#{updated})")
	public int addCategory(BookCategory category);
    @Select("Select id from book_category where name=#{name}")
	public Integer selectParentId(String name);
}
