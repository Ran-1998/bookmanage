package com.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.book.service.FileService;
import com.book.vo.EasyUIFile;

/**
 * @author Ran
 * @date 2020年3月15日
 */
@RestController
public class FileController {

	@RequestMapping("/file")
	public String file(MultipartFile fileImage) {
		
		
		return "文件上传成功";
	}
	
	
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 实现商品文件上传
	 */
	@RequestMapping("/pic/upload")
	public EasyUIFile fileUpload(MultipartFile uploadFile) {
		
		return fileService.fileUpload(uploadFile);
	}

}
