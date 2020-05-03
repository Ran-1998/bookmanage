package com.book.controller;


import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.book.dao.UserMsgDao;
import com.book.entity.User;
import com.book.service.UserMessageService;
import com.book.vo.JsonResult;

@Controller
@RequestMapping("/userMsg")
public class UserMessageContrller {
	
	@Autowired
	private UserMsgDao userMsgDao;
	
	@Autowired
	private UserMessageService userMessageService;
	
	static Integer id;
	
	@RequestMapping("/userName")
	public @ResponseBody JsonResult userName() {
		//UserController uc = new UserController();
		//String phone = uc.phone;
		User user=(User)SecurityUtils.getSubject().getPrincipal();
		JsonResult js = new JsonResult();
		js.setData(userMsgDao.userMsg(user.getUsername()));
		return js;
	}
	
	@RequestMapping("/history")
	public  @ResponseBody
    JsonResult doFindHistoryByStudentId(@RequestParam Integer pageCurrent) {
		//System.out.println("history");
		//Integer studentId = id;
		//System.out.println(id);
		JsonResult js = new JsonResult();
        js.setData(userMessageService.userHistory(pageCurrent));
        return js;
	}
	
	@RequestMapping("/doUpdatePassword")
	public @ResponseBody JsonResult doUpdatePassword(String user,String pwd, String newPwd, String cfgPwd) {
		JsonResult js = new JsonResult();
		js.setData(userMessageService.updatePassword(user,pwd, newPwd, cfgPwd));
		return js;
	 }
	
	@RequestMapping("/doBorrow")
	public @ResponseBody JsonResult doBorrow(@RequestParam Integer bookId) {
		JsonResult js = new JsonResult();
		js.setData(userMessageService.borrowBook(bookId));
		return js;
	}
	
	@RequestMapping("/review")
	public @ResponseBody JsonResult doReview(@RequestParam Integer bookId,@RequestParam String review) {
		//System.out.println(bookId+review);
		JsonResult js = new JsonResult();
		js.setState(userMessageService.reviewBook(bookId,review));
		return js;
	}
	
}
