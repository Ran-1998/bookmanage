package com.book.vo;

import com.book.pojo.BookCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ran
 * @date 2020年3月8日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EasyUICatTree extends BookCategory{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String state;
}
