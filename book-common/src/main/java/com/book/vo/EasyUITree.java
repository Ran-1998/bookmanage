package com.book.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EasyUITree implements Serializable {
	/**
	 * @author Ran
	 * @date 2020年2月29日
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String text;
	private String state;
}
