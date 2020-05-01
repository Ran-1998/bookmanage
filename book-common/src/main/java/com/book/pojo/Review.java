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
 * @date 2020年5月1日
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true) // 表示JSON转化时忽略未知属性
@TableName("user_review")
public class Review {
	@TableId(type = IdType.AUTO)
	private Long id;
	private Long userId;
	private Long bookId;
	private String review;
	private Date created;
	private String userName;
	private Long heat;

}
