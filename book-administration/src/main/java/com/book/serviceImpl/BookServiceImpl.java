package com.book.serviceImpl;

import java.text.ParseException;
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
import com.book.annotation.CacheFind;
import com.book.annotation.RequiredLog;
import com.book.mapper.BookMapper;
import com.book.mapper.CategoryMapper;
import com.book.pojo.Book;
import com.book.pojo.BookCategory;
import com.book.service.BookService;
import com.book.util.CategoryUtil;
import com.book.vo.BookVo;
import com.book.vo.EasyUITable;
import com.book.vo.FindBookVo;

/**
 * @author Ran
 * @date 2020年2月29日
 */
@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private CategoryMapper categoryMapper;
	
	private Date publicationdate;

	// 分页查询
	@RequiresPermissions("sys_book_view")
	@RequiredLog("查询图书")
	//@CacheFind(seconds=100)
	@Override
	public EasyUITable findBookBypage(FindBookVo findBookVo) {
		// TODO Auto-generated method stub
		// mybatis api
		/*
		 * int total = bookMapper.selectCount(null); int start = (page - 1) * rows;
		 * List<Book> bookList = bookMapper.findItemByPage(start, rows);
		 */
		Integer page = findBookVo.getPage();
		Integer rows = findBookVo.getRows();
		if (page==null && rows==null) {
			page=0;
			rows=0;
		}
		String author = findBookVo.getAuthor();
		Long catid = findBookVo.getCatid();
		String date1 = findBookVo.getData1();
		String date2 = findBookVo.getData2();
		String name = findBookVo.getName();
		Page<Book> tempPage = new Page<>(page, rows);
		QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
		queryWrapper.orderByDesc("updated");
		if (name != null && name != "") {
			queryWrapper.like("bookName", name);
		}
		if (author != null && author != "") {
			queryWrapper.like("author", author);
		}
		if (catid != null) {
			selectByCid(catid, queryWrapper);
		} 
		if (date1!=null&&date2!=null&&!date1.equals("")&&!date2.equals("")) {
			queryWrapper.between("publicationDate", date1, date2);
		}	
		IPage<Book> IPage = bookMapper.selectPage(tempPage, queryWrapper);
		int total = (int) IPage.getTotal();
		List<Book> bookList = IPage.getRecords();
		EasyUITable ey = new EasyUITable(total, bookList);
		return ey;
	}
	@RequiresPermissions("sys_book_add")
	@RequiredLog("新增图书")
	@Override
	public void saveBook(BookVo bookvo) {
		// TODO Auto-generated method stub
		Book book = voToBook(bookvo);
		book.setUpdated(new Date()).setCreated(new Date());
		bookMapper.InsertBook(book);
	}
	
	@RequiresPermissions("sys_book_update")
	@RequiredLog("更新图书")
	@Override
	public void updateBook(BookVo bookvo) {
		// TODO Auto-generated method stub
		Book book = voToBook(bookvo);
		book.setUpdated(new Date());
		bookMapper.updateById(book);
	}
	
	
	@RequiredLog("更新图书状态")
	@Override
	public void updateStatus(Long[] ids, int status) {
		// TODO Auto-generated method stub
		Book book = new Book();
		book.setStatus(Long.valueOf(status)).setUpdated(new Date());
		UpdateWrapper<Book> updateWrapper = new UpdateWrapper<>();
		List<Long> idList = Arrays.asList(ids);
		updateWrapper.in("id", idList);
		bookMapper.update(book, updateWrapper);
	}
	
	@RequiresPermissions("sys_book_delete")
	@RequiredLog("删除图书")
	@Override
	public void deleteBook(Long[] ids) {
		// TODO Auto-generated method stub
		List<Long> idList = Arrays.asList(ids);
		bookMapper.deleteBatchIds(idList);
	}

	private Book voToBook(BookVo bookvo) {
		try {
			if (bookvo.getPublicationdate()!="") {
				
				publicationdate = CategoryUtil.stringtoDate(bookvo.getPublicationdate());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Book book = new Book();
		Integer cid = bookvo.getCid();
		String bookname = bookvo.getBookname();
		String author = bookvo.getAuthor();
		String isbn = bookvo.getIsbn();
		String press = bookvo.getPress();
		String image = bookvo.getImage();
		String briefintroduction = bookvo.getBriefintroduction();
		book.setAuthor(author).setBookname(bookname).setBriefintroduction(briefintroduction).setCid(cid).setImage(image).setIsbn(isbn).setPress(press).setPublicationdate(publicationdate)
				.setId(bookvo.getId()).setStatus(1l);
		return book;
	}

	// cid查询处理方法
	private void selectByCid(Long catid, QueryWrapper<Book> queryWrapper) {
		BookCategory selectById = categoryMapper.selectById(catid);
		if (!selectById.getIsParent()) {
			queryWrapper.eq("cid", catid);
		} else {
			if (selectById.getParentId() == 0) {
				QueryWrapper<BookCategory> queryWrapperCat = new QueryWrapper<BookCategory>();
				queryWrapperCat.eq("parent_id", catid);
				List<BookCategory> selectList = categoryMapper.selectList(queryWrapperCat);
				// System.out.println(selectList);
				List<Long> cidList = new ArrayList<Long>();
				for (BookCategory bookCategory : selectList) {
					cidList.add(bookCategory.getId());
				}
				QueryWrapper<BookCategory> queryWrapperCat2 = new QueryWrapper<BookCategory>();
				queryWrapperCat2.in("parent_id", cidList);
				List<BookCategory> selectList2 = categoryMapper.selectList(queryWrapperCat2);
				if (selectList2.size() == 0) {
					queryWrapper.in("cid", cidList);
				} else {
					List<Long> cidList2 = new ArrayList<Long>();
					for (BookCategory bookCategory : selectList2) {
						cidList2.add(bookCategory.getId());
					}
						queryWrapper.in("cid", cidList2);
					
				}
			} else {
				QueryWrapper<BookCategory> queryWrapperCat = new QueryWrapper<BookCategory>();
				queryWrapperCat.eq("parent_id", catid);
				List<BookCategory> selectList = categoryMapper.selectList(queryWrapperCat);
				List<Long> cidList = new ArrayList<Long>();
				for (BookCategory bookCategory : selectList) {
					cidList.add(bookCategory.getId());
				}
				if (cidList.size()!=0) {
					queryWrapper.in("cid", cidList);
				}else {
					queryWrapper.eq("cid", catid);
				}
			}
		}
	}

	@Override
	public String findBookName(Long id) {
		// TODO Auto-generated method stub
		return bookMapper.selectById(id).getBookname();
	}

}
