package com.book.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.pojo.Role;

/**
 * @author Ran
 * @date 2020年4月4日
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role>{

	/**
	 * @return
	 */
	@Select("select * from sys_role")
	List<Role> selectAll();

}
