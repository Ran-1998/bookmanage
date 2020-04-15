package com.book.vo;

import com.book.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * @author Ran
 * @date 2020年2月29日
 */
@Data
@Accessors(chain = true)
public class BookVo extends BasePojo {
	/**
	 * 
	 */
	private Long id;
	private String isbn;  //isbn码
	private String bookname; //名字
	private String author; //作者
	private String press; //出版社
	private String briefintroduction;//简介
	private Long status; 
	private Integer cid;//分类
	private String publicationdate;//出版日期
	private String image;//图片地址
}
