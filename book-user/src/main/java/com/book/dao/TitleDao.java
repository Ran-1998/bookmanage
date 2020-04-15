package com.book.dao;

import com.book.entity.Book;
import com.book.entity.Category;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TitleDao {

    /** 查询总记录数 */
    //int getRowCount(@Param("hashCode")String hashCode);
	int getRowCount(@Param("category")String category);
	
	/** 查询parentId总记录数 */
    //int getRowCount(@Param("hashCode")String hashCode);
	int getParentIdRowCount(@Param("bookname")String bookname,
							@Param("parentId")String parentId);

    /** 查询类别 *///@Param("category")String category
    List<Category> queryTitle();
    
    List<Category> queryTwoTitle(Integer fid);
    
    List<Category> queryThreeTitle(Integer sid);

    /** 删除 */
    int deleteTitle(@Param("ids")Integer...ids);
    
    /** 查询类别里的书籍 */
    List<Book> queryBook(@Param("bookname")String bookname,
    					 @Param("parentId")String parentId,
    					 @Param("startIndex")Integer startIndex,
            			 @Param("pageSize")Integer pageSize);
    
    /** 查询书籍详细信息 */
    Book findParticularById(Integer id);
    
}
