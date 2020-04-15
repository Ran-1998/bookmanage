package com.book.serviceImp;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.book.annotation.RequiredLog;
import com.book.mapper.LogMapper;
import com.book.pojo.SysLog;
import com.book.service.LogService;
import com.book.vo.EasyUITable;
import com.book.vo.FindLogVo;

/**
 * @author Ran
 * @date 2020年3月29日
 */
@Service
public class LogServiceImpl implements LogService{

	@Autowired
	LogMapper logMapper;
	
	@Override
	public void saveLog(SysLog log) {
		// TODO Auto-generated method stub
		logMapper.insert(log);
	}
	
	@RequiredLog("查询日志")
	@Override
	public EasyUITable findLog(FindLogVo findLogVo) {
		// TODO Auto-generated method stub
		Integer page = findLogVo.getPage();
		Integer rows = findLogVo.getRows();
		String date1 = findLogVo.getDate1();
		String date2 = findLogVo.getDate2();
		String operation = findLogVo.getOperation();
		Object username = findLogVo.getUsername();
		Page<SysLog> tempPage = new Page<>(page, rows);
		QueryWrapper<SysLog> queryWrapper = new QueryWrapper<SysLog>();
		queryWrapper.orderByDesc("updated");
		if (username!=null&&username!="") {
			queryWrapper.like("username", username);
		}
		if (operation!=null&&operation!="") {
			queryWrapper.like("operation", operation);
		}
		if (date1!=null&&date2!=null&&!date1.equals("")&&!date2.equals("")) {
			queryWrapper.between("created", date1, date2);
		}	
		IPage<SysLog> IPage = logMapper.selectPage(tempPage, queryWrapper);
		int total = (int) IPage.getTotal();
		List<SysLog> bookList = IPage.getRecords();
		EasyUITable ey = new EasyUITable(total, bookList);
		return ey;
	}
	
	@RequiredLog("删除日志")
	@Override
	public void deleteLog(Long[] ids) {
		// TODO Auto-generated method stub
		List<Long> idList = Arrays.asList(ids);
		logMapper.deleteBatchIds(idList);
	}

}
