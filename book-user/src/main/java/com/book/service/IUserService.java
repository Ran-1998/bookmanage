package com.book.service;
 
import com.book.entity.User;

import java.util.Map;

public interface IUserService {

    /** 登录 */
    public User login(Map<String, String> map);
    
    /** 注册 */
    public void addUser(User user);

}