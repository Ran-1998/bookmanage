package com.book.controller;

import com.aliyuncs.exceptions.ClientException;

import com.book.service.VerifyService;
import com.book.util.Ab;
import com.book.vo.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Timer;
import java.util.TimerTask;

/** *  * @author liyongqiang * */
@Controller
@RequestMapping("/verify")
public class VerifyController {
	
	@Autowired
	private VerifyService verifyService;

	/**
	 * 获取、添加、删除验证码
	 * 加验证手机号码
	 * @throws ClientException
	 */
	@RequestMapping("/GetVerify")//final HttpServletRequest request
	public @ResponseBody
	JsonResult verifyUser(String username){
		//System.out.println(11111);
        JsonResult js=new JsonResult();
	    try {
			String phone = username;//request.getParameter("username");
			//System.out.println(phone);
			//查询手机号获取验证码次数
			int telCount = verifyService.queryTel(phone);
			if (telCount < 5) {
				//获得验证码
				String verify = Ab.getPhonemsg(phone);
				//System.out.println(verify);
				//手机号加入数据库
				verifyService.addTel(phone);
				//System.out.println(1);
				//添加验证码数据库
				verifyService.addVerifyCode(verify);
				//System.out.println(2);
				//System.out.println("添加");
				//定时
				this.removeAttrbute(verify);
				//System.out.println(3);
				//存入会话Session
				//final HttpSession session = request.getSession(true);
				//session.setAttribute("verify",verify);
				//CheckCodeMessage checkCodeMessage = new CheckCodeMessage(phone,verify);
				//定时
				//this.removeAttrbute(session,verify);
				//删除验证码
				//verifyService.deleteVerifyCode(verify);
			}else {
				//request.getSession().setAttribute("errormsg","每个号码每日最多获取5次，请明日在试！");
				//System.out.println("超过次数");
				js.setState(0);
				js.setMessage("0");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}

	public void removeAttrbute(String verify){
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				//删除session中存的验证码
				//session.removeAttribute(attrName);
				//删除数据库验证码
				//System.out.println("1删除验证码");
				verifyService.deleteVerifyCode(verify);
				//System.out.println("删除验证码");
				timer.cancel();
			}
		},1*60*1000);
	}

}
