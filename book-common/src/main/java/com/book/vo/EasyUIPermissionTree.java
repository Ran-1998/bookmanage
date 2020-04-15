package com.book.vo;

import com.book.pojo.Permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ran
 * @date 2020年4月5日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EasyUIPermissionTree extends Permission{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String state;
}
