package com.book.vo;

import com.book.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ran
 * @date 2020年4月4日
 */

@Data
@Accessors(chain = true)
public class AdminVo {

	private Integer page;
	private Integer rows;
	private String name;
	private Long roleId;
}
