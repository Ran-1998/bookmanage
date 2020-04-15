package com.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.pojo.User;
import com.book.service.UserService;
import com.book.vo.EasyUITable;
import com.book.vo.FindUserVo;
import com.book.vo.SysResult;

/**
 * @author Ran
 * @date 2020年3月21日
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
@Autowired
private UserService userService;

@RequestMapping("/query")
public EasyUITable findUser(FindUserVo findUserVo) {
	return userService.findUser(findUserVo);
}

@RequestMapping("/delete")
public SysResult deleteUser(Long ids[]) {
	
	userService.deleteUser(ids);
	return SysResult.success();
}

@RequestMapping("/update")
public SysResult update(User user) {
	
	userService.updateUser(user);
	return  SysResult.success();
	
}

@RequestMapping("/add")
public SysResult add(User user) {

	userService.addUser(user);
	return SysResult.success();
	
}
@RequestMapping("/findUserName")
public String findUserName(Long id) {
	return userService.findUserName(id);
}
@RequestMapping("/findPhone")
public Long findPhone(Long id) {
	return userService.findPhone(id);
}


}
