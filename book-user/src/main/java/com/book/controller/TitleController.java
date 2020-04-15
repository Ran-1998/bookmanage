package com.book.controller;


import com.book.dao.TitleDao;
import com.book.entity.Book;
import com.book.service.TitleService;
import com.book.vo.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/log")
public class TitleController {
    @Resource
    private TitleService titleService;
    
    @Autowired
    private TitleDao titleDao;

    public String doBookList(String parentId) {
    	
    	return "blogs/book_list";
    }
    
    @GetMapping("/doFindPageObjects")
    public @ResponseBody
    JsonResult doFindPageObjects() {
        //System.out.println(pageCurrent);
        JsonResult js = new JsonResult();
        js.setData(titleService.queryTitle());
        return js;
    }

    
    /** 根据书名、类别查询书籍基本信息 */
    @RequestMapping("/doFindParentId")
    public @ResponseBody JsonResult doFindParentId(String bookname,String parentId,@RequestParam Integer pageCurrent) {
    	if(bookname != null)
    		parentId = null;
    	JsonResult js = new JsonResult();
    	//根据parentId查询
    	js.setData(titleService.queryParentIdBook(bookname,parentId,pageCurrent));
    	return js;
    } 
    
    /** 根基书id查找书籍详细信息 */
    @RequestMapping("/doFindParticular")
    public @ResponseBody JsonResult doFindParticularById(@RequestParam Integer bookId) {
    	//根据id查询书籍详细信息
    	JsonResult js = new JsonResult();
    	Book book = titleDao.findParticularById(bookId);
    	js.setData(book);
    	//System.out.println(book.getISBN());
    	return js;
    }
    
    
     
}
