package com.book.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.annotation.RequiredLog;
import com.book.mapper.BookMapper;
import com.book.mapper.CategoryMapper;
import com.book.pojo.Book;
import com.book.pojo.BookCategory;
import com.book.service.CategoryService;
import com.book.vo.EasyUICatTree;
import com.book.vo.EasyUITree;
import com.book.vo.SelectCatVo;
import com.book.vo.SysResult;

/**
 * @author Ran
 * @date 2020年2月29日
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryMapper categoryMapper;

	@Autowired
	BookMapper bookMapper;

	@Override
	@RequiresPermissions("sys_book_add")
	@RequiredLog("新增图书类别")
	public int addCategory(BookCategory category) {
		// TODO Auto-generated method stub
		category.setCreated(new Date()).setUpdated(new Date());
		if (category.getParentId() == null) {
			category.setParentId(0l);
		} else if (category.getParentId() != 0) {
			BookCategory selectById = categoryMapper.selectById(category.getParentId());
			if (!selectById.getIsParent()) {
				selectById.setIsParent(true);
				categoryMapper.updateById(selectById);
			}
		}
		if (category.getIsParent() == null) {
			category.setIsParent(false);
		}
		return categoryMapper.addCategory(category);
	}
	
	@Override
	public BookCategory findNameBycid(Long cid) {
		// TODO Auto-generated method stub
		return categoryMapper.selectById(cid);
	}

	@Override
	public List<EasyUITree> findCatByParentId(Long parentId) {
		List<BookCategory> bookCatList = findBookCatByParentId(parentId);
		List<EasyUITree> treeList = new ArrayList<EasyUITree>(bookCatList.size());
		for (BookCategory bookCategory : bookCatList) {
			Long id = bookCategory.getId();
			String text = bookCategory.getName();
			String state = bookCategory.getIsParent() ? "closed" : "open";
			EasyUITree tree = new EasyUITree();
			tree.setId(id).setText(text).setState(state);
			treeList.add(tree);
		}
		return treeList;
	}

	private List<BookCategory> findBookCatByParentId(Long parentId) {
		// TODO Auto-generated method stub
		QueryWrapper<BookCategory> queryWrapper = new QueryWrapper<BookCategory>();
		queryWrapper.eq("parent_id", parentId);
		List<BookCategory> catList = categoryMapper.selectList(queryWrapper);
		return catList;
	}
	@RequiresPermissions("sys_book_view")
	@RequiredLog("查询图书类别")
	@Override
	public List<SelectCatVo> findCat() {
		// TODO Auto-generated method stub
		QueryWrapper<BookCategory> queryWrapper = new QueryWrapper<BookCategory>();
		queryWrapper.ne("parent_id", 0);
		queryWrapper.eq("is_parent", 1);
		List<BookCategory> selectList = categoryMapper.selectList(queryWrapper);
		// System.out.println(selectList);
		List<SelectCatVo> sv = new ArrayList<SelectCatVo>();
		for (BookCategory bookCategory : selectList) {
			SelectCatVo selectCatVo = new SelectCatVo();
			Long id = bookCategory.getId();
			String name = bookCategory.getName();
			selectCatVo.setCid(id).setName(name);
			sv.add(selectCatVo);
		}
		//System.out.println(sv);
		return sv;
	}
	@RequiresPermissions("sys_book_view")
	@RequiredLog("查询图书类别")
	@Override
	public List<EasyUICatTree> findAll(Long parentId) {
		// TODO Auto-generated method stub
		QueryWrapper<BookCategory> queryWrapper = new QueryWrapper<BookCategory>();
		queryWrapper.eq("parent_id", parentId);
		queryWrapper.orderByDesc("updated");
		List<BookCategory> catList = categoryMapper.selectList(queryWrapper);
		List<EasyUICatTree> catTrees = new ArrayList<>();
		for (BookCategory bookCategory : catList) {
			EasyUICatTree catTree = new EasyUICatTree();
			String state = bookCategory.getIsParent() ? "closed" : "open";
			catTree.setState(state).setId(bookCategory.getId()).setIsParent(bookCategory.getIsParent()).setName(bookCategory.getName()).setParentId(bookCategory.getParentId())
					.setStatus(bookCategory.getStatus()).setCreated(bookCategory.getCreated()).setUpdated(bookCategory.getUpdated());
			catTrees.add(catTree);
		}
		return catTrees;
}
	
	@RequiresPermissions("sys_book_view")
	@RequiredLog("查询图书类别")
	@Override
	public List<EasyUICatTree> select(String name) {
		// TODO Auto-generated method stub
		QueryWrapper<BookCategory> queryWrapper=new QueryWrapper<BookCategory>();
		queryWrapper.like("name", name);
		List<BookCategory> selectList = categoryMapper.selectList(queryWrapper);
		List<EasyUICatTree> catTrees = new ArrayList<>();
		for (BookCategory bookCategory : selectList) {
			EasyUICatTree catTree=new EasyUICatTree();
			String state = bookCategory.getIsParent() ? "closed" : "open";
			catTree.setState(state).setId(bookCategory.getId()).setIsParent(bookCategory.getIsParent()).setName(bookCategory.getName()).setParentId(bookCategory.getParentId())
			.setStatus(bookCategory.getStatus()).setCreated(bookCategory.getCreated()).setUpdated(bookCategory.getUpdated());
			catTrees.add(catTree);
		}
		return catTrees;
	}
	
	@RequiresPermissions("sys_book_delete")
	@RequiredLog("删除图书类别")
	@Override
	public void delectCat(Long[] ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			BookCategory selectById = categoryMapper.selectById(id);
			if (selectById != null) {
				delete(id, selectById);
			}
		}
	}
	@RequiresPermissions("sys_book_update")
	@RequiredLog("更新图书类别")
	@Override
	public void updateCat(Long id, Long newParentId, String name) {
		// TODO Auto-generated method stub
		BookCategory oldCat = categoryMapper.selectById(id);
		QueryWrapper<BookCategory> queryWrapper = new QueryWrapper<BookCategory>();
		queryWrapper.eq("parent_id", oldCat.getParentId());
		// xiugai
		BookCategory bookCategory = new BookCategory();
		if (newParentId == 1l) {
			newParentId = 0l;
		}
		bookCategory.setId(id).setParentId(newParentId).setStatus(1).setName(name).setUpdated(new Date());
		categoryMapper.updateById(bookCategory);

		List<BookCategory> Child = categoryMapper.selectList(queryWrapper);
		boolean isNull = false;
		if (Child.size() == 0) {
			isNull = true;
		}
		// 改旧父目录
		if (oldCat.getParentId() != 0 && oldCat.getIsParent()) {

			BookCategory bookCategory2 = new BookCategory();
			bookCategory2.setId(oldCat.getParentId()).setStatus(1).setUpdated(new Date());
			if (isNull) {
				bookCategory2.setIsParent(false);
			}else {bookCategory2.setIsParent(true);}
			categoryMapper.updateById(bookCategory2);
			// System.out.println("2");
		} else if (!oldCat.getIsParent()) {
			BookCategory bookCategory2 = new BookCategory();
			bookCategory2.setId(oldCat.getParentId()).setStatus(1).setUpdated(new Date());
			if (isNull) {
				bookCategory2.setIsParent(false);
			}else {bookCategory2.setIsParent(true);}
			categoryMapper.updateById(bookCategory2);
			BookCategory oldCat3 = categoryMapper.selectById(oldCat.getParentId());
			BookCategory bookCategory3 = new BookCategory();
			bookCategory3.setId(oldCat3.getParentId()).setStatus(1).setUpdated(new Date());
			categoryMapper.updateById(bookCategory3);
			// System.out.println("3");
		} else {
			// System.out.println("1");
		}

		// 改新父目录
		BookCategory newCatParent = categoryMapper.selectById(newParentId);
		if (newCatParent != null) {

			if (newCatParent.getParentId() == 0) {
				BookCategory bookCategory1 = new BookCategory();
				bookCategory1.setId(newParentId).setStatus(1).setIsParent(true).setUpdated(new Date());
				categoryMapper.updateById(bookCategory1);
			} else {
				BookCategory bookCategory1 = new BookCategory();
				bookCategory1.setId(newParentId).setStatus(1).setIsParent(true).setUpdated(new Date());
				categoryMapper.updateById(bookCategory1);

				BookCategory newCatParent2 = categoryMapper.selectById(newCatParent.getParentId());
				//System.out.println(newCatParent);
				//System.out.println(newCatParent2);

				BookCategory bookCategory2 = new BookCategory();
				bookCategory2.setId(newCatParent2.getId()).setIsParent(true).setStatus(1).setUpdated(new Date());
				categoryMapper.updateById(bookCategory2);

			}
		}

	}

	@Override
	public SysResult checkLV(Long parentId) {
		// TODO Auto-generated method stub
		SysResult result = new SysResult();
		BookCategory parentCat = categoryMapper.selectById(parentId);
		if (parentCat != null) {

			//System.out.println(parentCat);
			BookCategory parentCat2 = categoryMapper.selectById(parentCat.getParentId());
			//System.out.println(parentCat2);
			if (parentCat2 != null) {

				if (parentCat2.getParentId() != null && parentCat2.getParentId() != 0) {
					result.setMsg("最多三级目录");
					return result;
				} else {
					return SysResult.success();
				}
			}
		}

		return SysResult.success();

	}

	
	
	private void delete(Long id, BookCategory selectById) {
		Long parentId = selectById.getParentId();
		QueryWrapper<BookCategory> catQueryWrapper = new QueryWrapper<>();
		QueryWrapper<Book> queryWrapperBook = new QueryWrapper<>();
		QueryWrapper<BookCategory> catqueryWrapper2 = new QueryWrapper<>();
		if (!selectById.getIsParent()) {
			queryWrapperBook.eq("cid", id);
			bookMapper.delete(queryWrapperBook);
			categoryMapper.deleteById(id);
			
			BookCategory bookCategory=new BookCategory();
			//2
			bookCategory.setId(parentId).setUpdated(new Date());
			categoryMapper.updateById(bookCategory);
			BookCategory selectParentTWo = categoryMapper.selectById(parentId);
			if (selectParentTWo!=null) {
				Long parentId1 = selectParentTWo.getParentId();
				BookCategory selectParentOne = categoryMapper.selectById(parentId1);
				if (selectParentOne!=null) {
					BookCategory bookCategory2=new BookCategory();
					bookCategory2.setId(selectParentOne.getId()).setUpdated(new Date());
					categoryMapper.updateById(bookCategory2);
				}
			}
			
		} else if (parentId == 0) {
			catQueryWrapper.eq("parent_id", id);
			List<BookCategory> selectList2 = categoryMapper.selectList(catQueryWrapper);
			List<Long> cid2 = new ArrayList<>();
			for (BookCategory bookCategory : selectList2) {
				if (!bookCategory.getIsParent()) {
					Long id2 = bookCategory.getId();
					QueryWrapper<Book> queryWrapperBook1 = new QueryWrapper<>();
					queryWrapperBook1.eq("cid", id2);
					bookMapper.delete(queryWrapperBook1);
					categoryMapper.deleteById(id2);
				} else {
					cid2.add(bookCategory.getId());
				}
			}
			if (cid2.size() != 0) {
				catqueryWrapper2.in("parent_id", cid2);
				List<BookCategory> selectList3 = categoryMapper.selectList(catqueryWrapper2);
				List<Long> cid3 = new ArrayList<>();
				for (BookCategory bookCategory : selectList3) {
					cid3.add(bookCategory.getId());
				}
				if (cid3.size() != 0) {
					queryWrapperBook.in("cid", cid3);
					bookMapper.delete(queryWrapperBook);
					categoryMapper.deleteBatchIds(cid3);
				}
				QueryWrapper<Book> queryWrapperBook1 = new QueryWrapper<Book>();
				queryWrapperBook1.in("cid", cid2);
				List<Book> selectList = bookMapper.selectList(queryWrapperBook1);
				if (selectList != null) {
					List<Long> ids = new ArrayList<>();
					for (Book book : selectList) {
						ids.add(book.getId());
					}
					if (ids.size() != 0) {
						bookMapper.deleteBatchIds(ids);
					}
				}
				categoryMapper.deleteBatchIds(cid2);
			}
			QueryWrapper<Book> queryWrapperBook2 = new QueryWrapper<Book>();
			queryWrapperBook2.in("cid", id);
			List<Book> selectList = bookMapper.selectList(queryWrapperBook2);
			if (selectList != null) {
				List<Long> ids = new ArrayList<>();
				for (Book book : selectList) {
					ids.add(book.getId());
				}
				if (ids.size() != 0) {
					bookMapper.deleteBatchIds(ids);
				}
			}
			categoryMapper.deleteById(id);
		} else {
			catQueryWrapper.eq("parent_id", id);
			List<BookCategory> selectList = categoryMapper.selectList(catQueryWrapper);
			List<Long> cids = new ArrayList<>();
			for (BookCategory bookCategory : selectList) {
				cids.add(bookCategory.getId());
			}
			if (cids.size()!=0) {
			queryWrapperBook.in("cid", cids);
				bookMapper.delete(queryWrapperBook);
				categoryMapper.deleteBatchIds(cids);
			}
			QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
			queryWrapper.eq("cid", id);
			List<Book> selectList2 = bookMapper.selectList(queryWrapper);
			if (selectList2.size()!=0) {
				for (Book book : selectList2) {
					bookMapper.deleteById(book.getId());
				}
			}
			categoryMapper.deleteById(id);
			
			BookCategory bookCategory=new BookCategory();
			bookCategory.setId(parentId).setUpdated(new Date());
			categoryMapper.updateById(bookCategory);
		}
	}

	

}
