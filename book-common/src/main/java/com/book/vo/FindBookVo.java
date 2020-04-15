package com.book.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Ran
 * @date 2020年3月7日
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FindBookVo {
private Integer page;
private Integer rows;
private Long catid;
private String data1;
private String data2;
private String author; 
private String name;
}
