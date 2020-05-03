package com.book.service;

import com.book.entity.Borrow;
import com.book.vo.PageObject;

public interface UserMessageService {
										//,Integer studentId
	PageObject<Borrow> userHistory(Integer pageCurrent);

	int updatePassword(String user,String pwd, String newPwd, String cfgPwd);

	int borrowBook(Integer bookId);

	int reviewBook(Integer bookId, String review);
	
}
