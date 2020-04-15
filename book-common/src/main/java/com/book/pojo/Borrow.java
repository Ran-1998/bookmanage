package com.book.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ran
 * @date 2020年3月27日
 */
@Data
@Accessors (chain = true)
@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("user_borrow")
public class Borrow extends BasePojo{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Long id;
private Long userId;
private Long bookId;
private Long status;
private Date returnTime;
}
