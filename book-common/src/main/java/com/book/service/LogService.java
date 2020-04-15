package com.book.service;

import com.book.pojo.SysLog;
import com.book.vo.EasyUITable;
import com.book.vo.FindLogVo;

/**
 * @author Ran
 * @date 2020年3月29日
 */
public interface LogService {

	void saveLog(SysLog log);

	/**
	 * @param findLogVo
	 * @return
	 */
	EasyUITable findLog(FindLogVo findLogVo);

	/**
	 * @param ids
	 */
	void deleteLog(Long[] ids);
}
