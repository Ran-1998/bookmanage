package com.book.dao;
 
import com.book.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface IUserDao {

    /** 登录 */
    public User login(Map<String, String> map);
 
    User findUserByUserName(String username);
    
    /** 注册 */
	public void addUser(User user);


	
}