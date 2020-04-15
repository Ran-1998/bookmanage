package com.book.service;

import java.util.List;

import com.book.entity.Book;
import com.book.entity.Category;
import com.book.vo.PageObject;

public interface TitleService {

    /** 查询 String category */
    List<Category> queryTitle();

    PageObject<Book> queryParentIdBook(String bookname,String parentId,Integer pageCurrent);
}
