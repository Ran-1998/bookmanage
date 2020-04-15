package com.book.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ran
 * @date 2020年3月27日
 */
@Data
@Accessors(chain = true)
public class FindBorrowVo {
	private Integer page;
	private Integer rows;
	private String name;
	private String bookName;
	private Long status;
}
