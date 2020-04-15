package com.book.quartz;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.mapper.BorrowMapper;
import com.book.pojo.Borrow;
import com.book.util.DateUtil;

/**
 * @author Ran
 * @date 2020年4月4日
 */
@Component
public class BorrowQuartz extends QuartzJobBean {
	@Autowired
	private BorrowMapper borrowMapper;

	@Override
	@Transactional
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		
		QueryWrapper<Borrow> queryWrapper=new QueryWrapper<Borrow>();
		
		queryWrapper.eq("status", 1);
		
		List<Borrow> selectList = borrowMapper.selectList(queryWrapper);
		for (Borrow borrow : selectList) {
			Date returnTime = borrow.getReturnTime();
			Date date=new Date();
			if ( !DateUtil.compareDate(returnTime, date) && borrow.getStatus()!=0l) {
				borrow.setStatus(2l).setUpdated(new Date());
				borrowMapper.updateById(borrow);
				//System.out.println("修改");
			}
		}
		//System.out.println(selectList);
		System.out.println("执行");

	}

}
