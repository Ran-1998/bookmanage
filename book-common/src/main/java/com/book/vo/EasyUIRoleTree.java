package com.book.vo;

import java.util.List;

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
public class EasyUIRoleTree {
	/**
	 * @author Ran
	 * @date 2020年2月29日
	 */
	private Long id;
	private String text;
	private Boolean checked;
	private String state;
	private List<EasyUIRoleTree> children;
}
