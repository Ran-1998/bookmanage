package com.book.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.pojo.Admin;

/**
 * @author Ran
 * @date 2020年3月29日
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin>{
}
