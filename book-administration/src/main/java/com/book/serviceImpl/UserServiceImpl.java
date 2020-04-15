package com.book.serviceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.book.annotation.RequiredLog;
import com.book.mapper.UserMapper;
import com.book.pojo.User;
import com.book.service.UserService;
import com.book.vo.EasyUITable;
import com.book.vo.FindUserVo;

/**
 * @author Ran
 * @date 2020年3月21日
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@RequiresPermissions("sys_user_view")
	@RequiredLog("查询用户")
	@Override
	public EasyUITable findUser(FindUserVo findUserVo) {
		// TODO Auto-generated method stub
		Integer page = findUserVo.getPage();
		Integer rows = findUserVo.getRows();
				if (page==null && rows==null) {
					page=0;
					rows=0;
				};
		String name = findUserVo.getName();
		String className = findUserVo.getClassName();
		Long phone = findUserVo.getPhone();
		Long studentId = findUserVo.getStudentId();
		Page<User> tempPage = new Page<>(page, rows);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("updated");
		if (name != null && name != "") {
			queryWrapper.like("name", name);
		}
		if (className != null && className != "") {
			queryWrapper.like("class_Name", className);
		}
		if (phone != null) {
			queryWrapper.like("phone", phone);
		}
		if (studentId != null) {
			queryWrapper.like("student_Id", studentId);
		}

		IPage<User> IPage = userMapper.selectPage(tempPage, queryWrapper);
		int total = (int) IPage.getTotal();
		List<User> userList = IPage.getRecords();
		EasyUITable ey = new EasyUITable(total, userList);
		return ey;
	}

	@RequiresPermissions("sys_user_delete")
	@RequiredLog("删除用户")
	@Override
	public void deleteUser(Long[] ids) {
		// TODO Auto-generated method stub
		List<Long> idList = Arrays.asList(ids);
		userMapper.deleteBatchIds(idList);
	}

	@RequiresPermissions("sys_user_update")
	@RequiredLog("更新用户")
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		user.setUpdated(new Date());
		userMapper.updateById(user);
	}

	@RequiresPermissions("sys_user_add")
	@RequiredLog("添加用户")
	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		user.setUpdated(new Date()).setCreated(new Date());
		userMapper.insert(user);
	}

	@Override
	public String findUserName(Long id) {
		// TODO Auto-generated method stub
		return userMapper.selectById(id).getName();
	}

	@Override
	public Long findPhone(Long id) {
		// TODO Auto-generated method stub
		return userMapper.selectById(id).getPhone();
	}

}
