package com.book.serviceImpl;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
 
import javax.annotation.Resource;

import com.book.dao.IUserDao;
import com.book.entity.User;
import com.book.service.IUserService;
import org.springframework.stereotype.Service;
 

@Service("UserService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;
 
    public User login(Map<String, String> map) {
        // TODO Auto-generated method stub
        return userDao.login(map);
    }
    
    public void addUser(User user) {
    	Date date = new Date();
    	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    	user.setCreateTime(formatter.format(date));
    	//System.out.println(user);
    	userDao.addUser(user);
    }

 
}