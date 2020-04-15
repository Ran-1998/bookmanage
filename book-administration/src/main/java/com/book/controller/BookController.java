package com.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.service.BookService;
import com.book.vo.BookVo;
import com.book.vo.EasyUITable;
import com.book.vo.FindBookVo;
import com.book.vo.SysResult;

/**
 * @author Ran
 * @date 2020年2月29日
 */
@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bookService;
	// 查询数据信息
	@RequestMapping("/query")
	public EasyUITable findByPage(FindBookVo findBookVo) {
		return bookService.findBookBypage(findBookVo);
	}

	@RequestMapping("/save")
	public SysResult saveBook(BookVo bookvo) {
		bookService.saveBook(bookvo);
		return SysResult.success();
	}

	@RequestMapping("/update")
	public SysResult updateBook(BookVo bookvo) {
		bookService.updateBook(bookvo);
		return SysResult.success();
	}

	@RequestMapping("/instock")
	public SysResult instock(Long[] ids) {
		int status = 2;
		bookService.updateStatus(ids,status);
		return SysResult.success();
	}

	@RequestMapping("/reshelf")
	public SysResult reshelf(Long[] ids) {
		int status = 1;
		bookService.updateStatus(ids,status);
		return SysResult.success();
	}
	@RequestMapping("delete")
	public SysResult delete(Long[] ids) {
		bookService.deleteBook(ids);
		return SysResult.success();
	}
	@RequestMapping("findBookName")
	public String findBookName(Long id) {
		return bookService.findBookName(id);
	}
	
}
