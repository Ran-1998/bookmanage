package com.book.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Borrow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7213159246875297528L;
	private Integer id;
	private String studentId;
	private String bookId;
	private String bookName;
	private Integer status;
	private Date created;
	private String created1;
	private String returnTime;
	private String returnTime1;
	private String validDate;
	
}
