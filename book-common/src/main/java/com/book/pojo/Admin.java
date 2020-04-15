package com.book.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ran
 * @date 2020年3月29日
 */
@Data
@Accessors (chain = true)
@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("sys_admin")
public class Admin extends BasePojo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.AUTO)
	private Long id;
	private String name;
	private String password;
	private String salt;
	private String des;
	private Long roleId;
}
