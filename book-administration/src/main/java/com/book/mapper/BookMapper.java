package com.book.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.pojo.Book;
/**
 * @author Ran
 * @date 2020年2月29日
 */
@Mapper
public interface BookMapper extends BaseMapper<Book>{
	@Select("select * from book_information order by updated desc limit #{start},#{rows}")
	List<Book> findBookByPage(@Param("start")Integer start,@Param("rows")Integer rows);

	@Insert("insert into book_information values(null,#{isbn},#{bookname},#{author},#{press},#{publicationdate},#{briefintroduction},#{status},#{cid},#{image},#{created},#{updated})")
	int InsertBook(Book book);
}
