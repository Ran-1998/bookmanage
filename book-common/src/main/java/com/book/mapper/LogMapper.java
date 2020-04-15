package com.book.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.pojo.SysLog;
import com.book.pojo.User;

/**
 * @author Ran
 * @date 2020年3月21日
 */
@Mapper
public interface LogMapper extends BaseMapper<SysLog>{

}
