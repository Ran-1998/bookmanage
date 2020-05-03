package com.book.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.book.dao.UserMsgDao;
import com.book.entity.Borrow;
import com.book.entity.User;
import com.book.exception.ServiceException;
import com.book.service.UserMessageService;
import com.book.vo.PageObject;

@Service
public class UserMessageServiceImple implements UserMessageService{

	@Autowired
	private UserMsgDao userMsgDao;

	@Override
	public PageObject<Borrow> userHistory(Integer pageCurrent) {
		User user=(User)SecurityUtils.getSubject().getPrincipal();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		Date cdate = new Date();
		//Date rdate = new Date();
		Date vdate = new Date();
		//System.out.println("   user   "+user);
		//1.对参数进行校验
        if (pageCurrent==null || pageCurrent<1)throw new IllegalArgumentException("当前页码值无效");
        //2.查询总记录数并进行校验
        //System.out.println(hashCode);	studentId,
        int rowCount = userMsgDao.getHistoryRowCount(user.getId());
        //System.out.println(rowCount+" aaaa");
        if (rowCount==0)throw new ServiceException("没有找到对应记录");
        //3.查询当前页记录
        int pageSize = 8;
        int startIndex = (pageCurrent-1)*pageSize;//studentId,
        List<Borrow> records = userMsgDao.userHistory(user.getId(),startIndex,pageSize);
        //System.out.println(records);
        for(Borrow i : records) {
        	cdate = i.getCreated();
        	i.setCreated1(dateFormat.format(cdate));
        	String rdate = i.getReturnTime();
        	//System.out.println(rdate+"   0000");
        	if(rdate!=null)
        		i.setReturnTime1(rdate);
        	//vdate = new Date(cdate.getTime() + 30 * 24 * 60 * 60 * 1000L);
        	//i.setValidDate(dateFormat.format(vdate));
        	//逾期判断
//        	i.setOverdue("正常");
//        	if(vdate.getTime()-cdate.getTime()>30 * 24 * 60 * 60 * 1000L) {
//        		i.setOverdue("逾期");
//        	}
        }
        //System.out.println(records);
        //对查询结果进行封装
        return new PageObject<Borrow>(pageCurrent,pageSize,rowCount,records);
	}

	@Override
	public int updatePassword(String user,String pwd, String newPwd, String cfgPwd) {
		int row=0;
		//System.out.println("更改");
		//1.参数校验
    	//1.1非空验证
		if(StringUtils.isEmpty(user))
			throw new IllegalArgumentException("用户名不能为空");
    	if(StringUtils.isEmpty(pwd))
    		throw new IllegalArgumentException("原密码不能为空");
    	if(StringUtils.isEmpty(newPwd))
    		throw new IllegalArgumentException("新密码不能为空");
    	if(!newPwd.equals(cfgPwd))
    		throw new IllegalArgumentException("两次密码输入必须一致");
    	//原密码正确性验证
    	if(userMsgDao.verifyUser(user,pwd)==1) {
    		row=1;
    		userMsgDao.updatePassword(user,newPwd);
    		//throw new ServiceException("成功");
    		
    	}
    	if(row==0) throw new ServiceException("不成功");
		return row;
	}

	@Override
	public int borrowBook(Integer bookId) {
		int rows=0;
		User user=(User)SecurityUtils.getSubject().getPrincipal();
		//查询书籍状态
		int status = userMsgDao.bookStatus(bookId);
		if(status==1) {
			//更改书籍状态
			userMsgDao.changeBookStatus(bookId);
			//添加借阅表信息
			Date date = new Date();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			String vdate = dateFormat.format(new Date(date.getTime() + 30 * 24 * 60 * 60 * 1000L));
			userMsgDao.insertBorrow(user.getId(),bookId,dateFormat.format(date),vdate);
			rows=1;
		}
		return rows;
	}

	@Override
	public int reviewBook(Integer bookId, String review) {
		User user=(User)SecurityUtils.getSubject().getPrincipal();
		//System.out.println(user.getId());
		int rows=0;
		if(review==null) {
			rows=0;
		}else if (review!=null) {
			Date date = new Date();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			String created = dateFormat.format(date); 
			userMsgDao.reviewBook(user.getId(),bookId,review,created,user.getName());
			rows=1;
		}
		return rows;
	}

	
	
}
