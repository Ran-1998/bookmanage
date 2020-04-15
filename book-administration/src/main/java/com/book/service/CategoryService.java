package com.book.service;

import java.util.List;

import com.book.pojo.BookCategory;
import com.book.vo.EasyUICatTree;
import com.book.vo.EasyUITree;
import com.book.vo.SelectCatVo;
import com.book.vo.SysResult;
/**
 * @author Ran
 * @date 2020年2月29日
 */
public interface CategoryService {

	public int addCategory(BookCategory category);

	public BookCategory findNameBycid(Long cid);

	public List<EasyUITree> findCatByParentId(Long parentId);

	/**
	 * @return
	 */
	public List<SelectCatVo> findCat();

	/**
	 * @return
	 */
	public List<EasyUICatTree> findAll(Long parentId);

	/**
	 * @param ids
	 */
	public void delectCat(Long[] ids);

	/**
	 * @param id
	 * @param parentId
	 * @param name
	 */
	public void updateCat(Long id, Long parentId, String name);

	/**
	 * @param parentId
	 * @return
	 */
	public SysResult checkLV(Long parentId);

	/**
	 * @param name
	 * @return
	 */
	public List<EasyUICatTree> select(String name);
}
