package com.book.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.book.annotation.RequiredLog;
import com.book.mapper.BookMapper;
import com.book.mapper.BorrowMapper;
import com.book.mapper.UserMapper;
import com.book.pojo.Book;
import com.book.pojo.Borrow;
import com.book.pojo.User;
import com.book.service.BorrowService;
import com.book.util.DateUtil;
import com.book.vo.EasyUITable;
import com.book.vo.FindBorrowVo;

/**
 * @author Ran
 * @date 2020年3月27日
 */
@Service
public class BorrowServiceImpl implements BorrowService {

	@Autowired
	private BorrowMapper borrowMapper;

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private UserMapper userMapper;
	
	@RequiresPermissions("sys_user_view")
	@RequiredLog("查询借阅记录")
	@Override
	public EasyUITable findUser(FindBorrowVo findBorrowVo) {
		// TODO Auto-generated method stub
		Integer page = findBorrowVo.getPage();
		Integer rows = findBorrowVo.getRows();
		if (page==null && rows==null) {
			page=0;
			rows=0;
		}
		Long status = findBorrowVo.getStatus();
		String name = findBorrowVo.getName();
		String bookname = findBorrowVo.getBookName();
		QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
		QueryWrapper<User> userqueryWrapper = new QueryWrapper<>();
		QueryWrapper<Book> bookqueryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("updated");
		if (status != null) {
			queryWrapper.eq("status", status);
		}

		if (bookname != null && bookname != "") {
			bookqueryWrapper.like("bookName", bookname);
			List<Book> selectList = bookMapper.selectList(bookqueryWrapper);
			// System.out.println(selectList);
			for (Book book : selectList) {
				Long id = book.getId();
				if (borrowMapper.selectByBookId(id).size() != 0) {
					queryWrapper.eq("book_Id", id);
				}
			}
		}

		if (name != null && name != "") {
			userqueryWrapper.like("name", name);
			List<User> selectList = userMapper.selectList(userqueryWrapper);
			// System.out.println(selectList);
			for (User user : selectList) {
				Long id = user.getId();
				List<Borrow> selectByUserId = borrowMapper.selectByUserId(id);
				if (selectByUserId.size() != 0) {
					queryWrapper.eq("user_Id", id);
				}
			}

		}

		Page<Borrow> tempPage = new Page<>(page, rows);
		IPage<Borrow> IPage = borrowMapper.selectPage(tempPage, queryWrapper);
		int total = (int) IPage.getTotal();
		List<Borrow> userList = IPage.getRecords();
		List<Borrow> userLists = new ArrayList<Borrow>();
		Date date2 = new Date();
		for (Borrow borrow : userList) {
			Date date1 = borrow.getReturnTime();
			if (date1!=null) {
				boolean compareDate = DateUtil.compareDate(date1, date2);
			if (!compareDate && borrow.getStatus() != 0l) {
				borrow.setStatus(2l).setUpdated(new Date());
				borrowMapper.updateById(borrow);
			}
			}
			userLists.add(borrow);
		}
		EasyUITable ey = new EasyUITable(total, userLists);
		return ey;
	}
	
	@RequiresPermissions("sys_user_delete")
	@RequiredLog("删除借阅记录")
	@Override
	public void deleteBorrow(Long[] ids) {
		// TODO Auto-generated method stub
		List<Long> idList = Arrays.asList(ids);
		borrowMapper.deleteBatchIds(idList);
	}
	
	@RequiresPermissions("sys_user_update")
	@RequiredLog("还书")
	@Override
	public void returnBook(Long[] ids, Long[] bookIds) {
		// TODO Auto-generated method stub
		Borrow borrow = new Borrow();
		borrow.setStatus(0l).setUpdated(new Date());
		UpdateWrapper<Borrow> updateWrapper = new UpdateWrapper<>();
		List<Long> idList = Arrays.asList(ids);
		updateWrapper.in("id", idList);
		borrowMapper.update(borrow, updateWrapper);

		Book book = new Book();
		book.setStatus(1L).setUpdated(new Date());
		UpdateWrapper<Book> bookupdateWrapper = new UpdateWrapper<>();
		List<Long> bookidList = Arrays.asList(bookIds);
		bookupdateWrapper.in("id", bookidList);
		bookMapper.update(book, bookupdateWrapper);

	}
	
	@RequiresPermissions("sys_user_update")
	@RequiredLog("延时还书")
	@Override
	public void delayTime(Long[] ids, Long day, String[] returnTime) {
		// TODO Auto-generated method stub
		//System.out.println(returnTime);
		List<Date> returnTimes = new ArrayList<Date>();
		for (String date : returnTime) {
			returnTimes.add(DateUtil.StringtoDate(date));
		}
		if (day == null) {
			day = 0l;
		}
		List<Long> idList = Arrays.asList(ids);
		int index=0;
		for (Long id : idList) {
			Borrow borrow = new Borrow();
			borrow.setId(id).setStatus(1l).setReturnTime(DateUtil.addDate(returnTimes.get(index), day)).setUpdated(new Date());
			borrowMapper.updateById(borrow);
			index++;
		}
	}

}
