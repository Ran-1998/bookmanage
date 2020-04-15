package com.book.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Book implements Serializable {

	private static final long serialVersionUID = 5330581981520644377L;
	
	private Integer id;
	
	private String ISBN;

	private String imgUrl;
	
	private String bookName;
	
	private String author;
	
	private String bookPress;
	
	private String pressDate;
	
	private String bookParticular;
	
	private Integer status;
	
}
