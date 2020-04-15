package com.book.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ran
 * @date 2020年3月29日
 */

@Data
@Accessors(chain = true)
public class FindLogVo {
	
	private Integer page;
	private Integer rows;
	private String username;
	private String operation;
	private String date1;
	private String date2;
	
}
