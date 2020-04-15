package com.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author Ran
 * @date 2020年2月29日
 */
//通用页面跳转
@Controller
public class IndexController {
	
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		
		return moduleName;
	}
}
