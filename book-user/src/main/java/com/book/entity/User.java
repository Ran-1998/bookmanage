package com.book.entity;
 
import java.io.Serializable;

import lombok.Data;
 
@Data
public class User implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -1013367856496905619L;
	
	private Integer id;
    private Long username;
    private String password;
    private Long studentId;
    private String name;
    private String className;
    private String createTime;
    private String updateTime;
     
}