package com.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.pojo.BookCategory;
import com.book.service.CategoryService;
import com.book.vo.EasyUICatTree;
import com.book.vo.EasyUITree;
/**
 * @author Ran
 * @date 2020年2月29日
 */
import com.book.vo.SelectCatVo;
import com.book.vo.SysResult;

import lombok.EqualsAndHashCode;

@RestController
@RequestMapping("/book/cat")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/queryCatName")
	public String findBookName(Long cid) {
		if (cid != 0 && cid != null) {
			return categoryService.findNameBycid(cid).getName();
		} else {
			return "无";
		}
	}

	@RequestMapping("/list")
	public List<EasyUITree> findParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		return categoryService.findCatByParentId(parentId);
	}

	@RequestMapping("/listupdate")
	public List<EasyUITree> findId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EasyUITree> findCatByParentId = categoryService.findCatByParentId(parentId);
		if (parentId == 0) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(1l).setState("closed").setText("无");
			findCatByParentId.add(easyUITree);
		}
		return findCatByParentId;
	}

	@RequestMapping("/listAll")
	public List<EasyUICatTree> findAll(@RequestParam(value = "id", defaultValue = "0") Long parentId) {

		return categoryService.findAll(parentId);

	}

	@RequestMapping("/findCat")
	public List<SelectCatVo> findCat() {

		return categoryService.findCat();
	}

	@RequestMapping("/delete")
	public SysResult deleteCat(Long ids[]) {
		categoryService.delectCat(ids);
		return SysResult.success();
	}

	@RequestMapping("/add")
	public SysResult addCat(BookCategory category) {
		categoryService.addCategory(category);
		return SysResult.success();
	}

	@RequestMapping("/update")
	public SysResult updateCat(Long id, Long parentId, String name) {

		categoryService.updateCat(id, parentId, name);

		return SysResult.success();
	}

	@RequestMapping("/checkLV")
	public SysResult checkLv(Long parentId) {
		return categoryService.checkLV(parentId);
	}
	
	@RequestMapping("/select")
	public List<EasyUICatTree> select(String name) {
		
		return categoryService.select(name);		
	}
}
