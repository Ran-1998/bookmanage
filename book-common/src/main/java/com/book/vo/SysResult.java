package com.book.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ran
 * @date 2020年2月29日
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysResult {

	private Integer status; // 200成功 201失败
	private String msg; // 提示信息
	private Object data; // 返回的数据
	// 成功
	public static SysResult success() {
		return new SysResult(200, null, null);
	}

	// 成功返回数据
	public static SysResult success(Object data) {
		return new SysResult(200, null, data);
	}

	// 成功之后返回msg
	public static SysResult success(String msg, Object data) {
		return new SysResult(200, msg, data);
	}

	// 失败
	public static SysResult fail() {
		return new SysResult(201, "失败", null);
	}
	
}