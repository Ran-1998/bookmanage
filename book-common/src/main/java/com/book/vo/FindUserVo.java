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
public class FindUserVo {
private Integer page;
private Integer rows;
private String name;
private Long phone;
private Long studentId;
private String className;
}
