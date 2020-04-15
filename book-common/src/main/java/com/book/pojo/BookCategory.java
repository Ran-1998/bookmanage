package com.book.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * @author Ran
 * @date 2020年2月29日
 */@Data
@Accessors (chain = true)
@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("book_category")
public class BookCategory extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.AUTO)
	private Long id;  //类别id
	private Long parentId; //父类别类别id
	private String name; //类别名
	private Integer status;//类别状态
	private Boolean isParent;//是否为父类
	
}
