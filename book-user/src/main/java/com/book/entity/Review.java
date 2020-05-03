package com.book.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Review implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7916720708414069799L;
	
	private Integer id;

	private Integer bookId;
	
	private String name;
	
	private String review;
	
	private String created;
	
	private Integer heat;
	
}
