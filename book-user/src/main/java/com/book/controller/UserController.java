package com.book.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.book.entity.User;
import com.book.service.IUserService;
import com.book.service.VerifyService;
import com.book.vo.JsonResult;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private VerifyService verifyService;
	
	static String phone = null;

	@RequestMapping("doIndexUI")
	public String doIndexUI(){
		return "login";
	}

	@RequestMapping("add_user")
	public String addUser(){
		return "add_user";
	}
	
//	/**   登录     *      * @param req     * @param mv     * @return     */    
//	@RequestMapping("login")
//	public ModelAndView login(HttpServletRequest result, ModelAndView mv) {
//		Map<String, String> map = new HashMap<String, String>();        
//		map.put("username", result.getParameter("username"));
//		map.put("password", result.getParameter("password"));
//		phone = result.getParameter("username");
//		System.out.println(phone);
//		int telCount = verifyService.queryTel(phone);
//		if (telCount < 6) {
//			verifyService.addTel(phone);
//			User user = userService.login(map);
//			if (user != null) {
//				//登录成功进入首页
//				mv.setViewName("blogs/homepage");
//			} else {
//				//登录失败回到登录页面
//				mv.addObject("message", "用户名或密码输入错误,请重新输入");
//				mv.setViewName("index");
//			}
//		}else {
//			mv.addObject("message", "今日已经到达尝试上限，明日在试");
//			mv.setViewName("index");
//			//mv.setView("1");
//		}
//		return mv;
//	}
	
	/**   登录     *      * @param req     * @param mv     * @return     */    
	@RequestMapping("/login")
	public @ResponseBody
	JsonResult login(String username,String password) {
		
		//System.out.println(username);
		//1.封装用户信息
		UsernamePasswordToken token=new UsernamePasswordToken();
		token.setUsername(username);
		token.setPassword(password.toCharArray());
		//System.out.println(token);
		//获取Subject对象
		Subject subject=SecurityUtils.getSubject();
		//System.out.println("subject   "+subject);
		//提交用户信息进行认证
		subject.login(token);
		//System.out.println(123);
		return new JsonResult();
	}
	
	/** 注册 */
	@RequestMapping("addUser")
	public String addUser(User user,String code) {
		String mv="add_user";
		//System.out.println(user);
		//System.out.println(code);
		/** 查询验证码 */
		int verifyCount = verifyService.queryVerifyCode(code);
		//System.out.println(1);
		if(verifyCount!=0) {
			//System.out.println(2);
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			user.setCreateTime(formatter.format(date));
			//System.out.println(formatter.format(date));
			userService.addUser(user);
			mv= "index";
		}
		return mv;
	}
	
	@RequestMapping("/{moduleName}")
	public String page(@PathVariable String moduleName){
		//System.out.println(moduleName);
		return "blogs/"+moduleName;
	}
	
}
