package com.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("doPageUI")
    public String doPageUI() {
        return "../common/page";
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

