package com.book.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Ran
 * @date 2020年3月29日
 */

@Data
@Accessors (chain = true)
@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("sys_logs")
public class SysLog extends BasePojo{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String username;
    private String operation;
    private String method;
    private String params;
    private Long time;
    private String ip;
    
}
