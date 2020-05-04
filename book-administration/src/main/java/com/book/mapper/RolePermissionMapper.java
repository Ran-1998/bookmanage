package com.book.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.pojo.RolePermission;

/**
 * @author Ran
 * @date 2020年4月4日
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission>{
	public void deleteRoleByIds(@Param("idList") List<Long> idList);
}
