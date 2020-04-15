package com.book.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ran
 * @date 2020年4月4日
 */
@Data
@Accessors(chain = true)
public class PermissionVo {
	private Integer id;
	private String permissionName;
	private String permission;
}
