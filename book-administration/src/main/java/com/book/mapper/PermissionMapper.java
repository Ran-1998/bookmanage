package com.book.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.pojo.Permission;

/**
 * @author Ran
 * @date 2020年4月4日
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission>{

}
