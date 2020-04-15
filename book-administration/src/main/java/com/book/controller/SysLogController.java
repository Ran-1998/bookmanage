package com.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.service.LogService;
import com.book.vo.EasyUITable;
import com.book.vo.FindLogVo;
import com.book.vo.SysResult;

import lombok.EqualsAndHashCode;

/**
 * @author Ran
 * @date 2020年3月29日
 */
@RequestMapping("/log")
@RestController
public class SysLogController {

	@Autowired
	LogService logService;
	@RequestMapping("/query")
	public EasyUITable queryLog(FindLogVo findLogVo) {
		return logService.findLog(findLogVo);
	}
	
	@RequestMapping("/delete")
	public SysResult deleteLog(Long[] ids) {
		logService.deleteLog(ids);
		return SysResult.success();
	}
}
