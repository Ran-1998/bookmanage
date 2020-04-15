package com.book.vo;

import java.io.Serializable;
import java.util.List;

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
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITable implements Serializable{

	/**
	 * 
	 */
	//easyui vo 对象
	private static final long serialVersionUID = 1L;
	private Integer total;
	private List<?> rows;
}
