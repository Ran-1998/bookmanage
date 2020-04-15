package com.book.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.book.vo.EasyUIFile;

/**
 * @author Ran
 * @date 2020年3月15日
 */
@Service
public interface FileService {

	/**
	 * @param uploadFile
	 * @return
	 */
	EasyUIFile fileUpload(MultipartFile uploadFile);

}
