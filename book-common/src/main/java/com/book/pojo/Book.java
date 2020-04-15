package com.book.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * @author Ran
 * @date 2020年2月29日
 */
@Data
@Accessors (chain = true)
@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("book_information")
public class Book extends BasePojo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.AUTO)
	private Long id;
	private String isbn;  //isbn码
	private String bookname; //名字
	private String author; //作者
	private String press; //出版社
	private String briefintroduction;//简介
	private Long status; 
	private Integer cid;//分类
	private Date publicationdate;//出版日期
	private String image;//图片地址

}
