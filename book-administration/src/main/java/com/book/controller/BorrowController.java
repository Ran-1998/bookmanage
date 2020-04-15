package com.book.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.pojo.User;
import com.book.service.BorrowService;
import com.book.service.UserService;
import com.book.vo.EasyUITable;
import com.book.vo.FindUserVo;
import com.book.vo.SysResult;
import com.book.vo.FindBorrowVo;

/**
 * @author Ran
 * @date 2020年3月21日
 */
@RestController
@RequestMapping("/borrow")
public class BorrowController {
@Autowired
private BorrowService borrowService;

@RequestMapping("/query")
public EasyUITable findUser(FindBorrowVo findBorrowVo) {
	
	return borrowService.findUser(findBorrowVo);
}

@RequestMapping("/delete")
public SysResult deleteBorrow(Long ids[]) {

	borrowService.deleteBorrow(ids);
	return SysResult.success();
}
@RequestMapping("/return")
public SysResult returnBook(Long ids[],Long[] bookIds) {
	borrowService.returnBook(ids,bookIds);
	return SysResult.success();
}
@RequestMapping("/delay")
public SysResult delayTime(Long ids[],Long day,String[] returnTime) {
	borrowService.delayTime(ids,day,returnTime);
	return SysResult.success();
}

}
