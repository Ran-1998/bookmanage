package com.book.serviceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.book.annotation.RequiredLog;
import com.book.mapper.AdminMapper;
import com.book.pojo.Admin;
import com.book.pojo.Book;
import com.book.service.AdminService;
import com.book.vo.AdminVo;
import com.book.vo.EasyUITable;

/**
 * @author Ran
 * @date 2020年3月29日
 */
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminMapper adminMapper;
	@RequiresPermissions("sys_admin_add")
	@RequiredLog("新增系统用户")
	@Override
	public void save(Admin admin) {
		// TODO Auto-generated method stub
		String salt = UUID.randomUUID().toString();
		admin.setSalt(salt);
		SimpleHash simpleHash = new SimpleHash("MD5", admin.getPassword(), salt, 1);
		admin.setPassword(simpleHash.toHex());
		admin.setCreated(new Date()).setUpdated(new Date());
		// System.out.println(admin);
		adminMapper.insert(admin);
	}
	@RequiresPermissions("sys_admin_view")
	@RequiredLog("查询系统用户")
	@Override
	public EasyUITable query(AdminVo adminVo) {
		Integer page = adminVo.getPage();
		Integer rows = adminVo.getRows();
		if (page==null && rows==null) {
			page=0;
			rows=0;
		}
		Long roleId = adminVo.getRoleId();
		String name = adminVo.getName();
		Page<Admin> tempPage = new Page<>(page, rows);
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
		queryWrapper.orderByDesc("updated");
		if (roleId!=null) {
			queryWrapper.eq("role_Id", roleId);
		}
		if (name!=null && name!="") {
			queryWrapper.like("name", name);
		}
		IPage<Admin> IPage = adminMapper.selectPage(tempPage, queryWrapper);
		int total = (int) IPage.getTotal();
		List<Admin> bookList = IPage.getRecords();
		EasyUITable ey = new EasyUITable(total, bookList);
		return ey;
	}
	@RequiresPermissions("sys_admin_update")
	@RequiredLog("更新系统用户")
	@Override
	public void upadte(Admin admin) {
		// TODO Auto-generated method stub
		String password = admin.getPassword();
		if (password != null && password != "") {
			String salt = UUID.randomUUID().toString();
			admin.setSalt(salt);
			SimpleHash simpleHash = new SimpleHash("MD5", password, salt, 1);
			admin.setPassword(simpleHash.toHex());
		} else {
			admin.setPassword(null);
		}
		admin.setUpdated(new Date());
		System.out.println(admin);
		adminMapper.updateById(admin);
	}
	@RequiresPermissions("sys_admin_delete")
	@RequiredLog("删除系统用户")
	@Override
	public void delete(Long[] ids) {
		// TODO Auto-generated method stub
		List<Long> idList = Arrays.asList(ids);
		adminMapper.deleteBatchIds(idList);

	}

}
