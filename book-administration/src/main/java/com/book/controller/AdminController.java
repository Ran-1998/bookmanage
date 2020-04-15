package com.book.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.book.pojo.Admin;
import com.book.pojo.Role;
import com.book.service.AdminService;
import com.book.util.ShiroUtils;
import com.book.vo.AdminVo;
import com.book.vo.EasyUITable;
import com.book.vo.SysResult;

/**
 * @author Ran
 * @date 2020年3月29日
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@RequestMapping("/add")
	@ResponseBody
	public SysResult saveUser(Admin admin) {
		adminService.save(admin);
		return SysResult.success();
	}

	@RequestMapping("/doLoginUI")
	public String doLoginUI() {
		return "login";
	}

	@RequestMapping("/login")
	@ResponseBody
	public SysResult Login(String name, String password) {
		// 获取Subject对象
		Subject subject = SecurityUtils.getSubject();
		// 对用户进行封装
		UsernamePasswordToken token = new UsernamePasswordToken(name, password);
		// 对用户信息进行身份认证
		subject.login(token);
		//System.out.println(token);
		return SysResult.success();
	}
	
	@RequestMapping("/getName")
	@ResponseBody
	public String getName() {
		return ShiroUtils.getUserName();
	}

	@RequestMapping("/query")
	@ResponseBody
	public EasyUITable query(AdminVo adminVo) {
		return adminService.query(adminVo);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public SysResult upadte(Admin admin) {
				adminService.upadte(admin);
				return SysResult.success();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult delete(Long[] ids) {
				adminService.delete(ids);
				return SysResult.success();
	}
	
}
