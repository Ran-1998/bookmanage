package com.book.serviceImpl;

import com.book.dao.TitleDao;
import com.book.entity.Book;
import com.book.entity.Category;
import com.book.exception.ServiceException;
import com.book.service.TitleService;
import com.book.vo.JsonResult;
import com.book.vo.PageObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TitleServiceImpl implements TitleService {
	@Autowired
    private TitleDao titleDao;

    @Override//String category
    public List<Category> queryTitle() {
        List<Category> records = titleDao.queryTitle();
        for(Category i : records) {
        	i.setTwoCategory(titleDao.queryTwoTitle(i.getId()));
        	for(Category j : i.getTwoCategory()) {
        		j.setThreeCategory(titleDao.queryThreeTitle(j.getId()));
        	}
        }
        //对查询结果进行封装
        return records;
    }


	@Override
	public PageObject<Book> queryParentIdBook(String bookname,String parentId,Integer pageCurrent) {
		//1.对参数进行校验
        if (pageCurrent==null || pageCurrent<1)throw new IllegalArgumentException("当前页码值无效");
        //2.查询总记录数并进行校验
        //System.out.println(hashCode);
        int rowCount = titleDao.getParentIdRowCount(bookname,parentId);
        //System.out.println(rowCount+" aaaa");
        //if (rowCount==0)throw new ServiceException("没有找到对应记录");
        //3.查询当前页记录
        int pageSize = 4;
        int startIndex = (pageCurrent-1)*pageSize;
		List<Book> records = titleDao.queryBook(bookname,parentId,startIndex,pageSize);
		//System.out.println(records);
		return new PageObject<>(pageCurrent,pageSize,rowCount,records);
	}
}
