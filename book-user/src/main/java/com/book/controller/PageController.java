package com.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("doPageUI")
    public String doPageUI() {
    	//System.out.println(10);
        return "../common/page";
    }
    
    @RequestMapping("doPageUI2")
    public String doPageUI2() {
    	//System.out.println(10);
        return "../common/page2";
    }
    
    @RequestMapping("/doHonmeUI")
    public String doHomeUI(){
    	return "starter";
    }
    
//    @RequestMapping("{module}/{moduleUI}")
//	public String doModuleUI(@PathVariable String moduleUI) {
//		return "sys/"+moduleUI;
//	}
    
}

