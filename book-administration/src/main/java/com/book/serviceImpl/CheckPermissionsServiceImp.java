package com.book.serviceImpl;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;

import com.book.service.CheckPermissions;

/**
 * @author Ran
 * @date 2020年5月21日
 */
@Service
public class CheckPermissionsServiceImp implements CheckPermissions{

	@Override
	@RequiresPermissions("sys_book_view")
	public void checkBookQuery() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@RequiresPermissions("sys_book_add")
	public void checkBookAdd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@RequiresPermissions("sys_book_delete")
	public void checkBookDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@RequiresPermissions("sys_book_update")
	public void checkBookUpadte() {
		// TODO Auto-generated method stub
		
	}
	
	@RequiresPermissions("sys_user_update")
	@Override
	public void checkUserUpdate() {
		// TODO Auto-generated method stub
		
	}
	@RequiresPermissions("sys_user_view")
	@Override
	public void checkUserQuery() {
		// TODO Auto-generated method stub
		
	}
	@RequiresPermissions("sys_user_delete")
	@Override
	public void checkUserDelete() {
		// TODO Auto-generated method stub
		
	}
	@RequiresPermissions("sys_user_add")
	@Override
	public void checkUserAdd() {
		// TODO Auto-generated method stub
		
	}
	@RequiresPermissions("sys_admin_add")
	@Override
	public void checkAdminAdd() {
		// TODO Auto-generated method stub
		
	}
	@RequiresPermissions("sys_admin_delete")
	@Override
	public void checkAdminDelete() {
		// TODO Auto-generated method stub
		
	}
	@RequiresPermissions("sys_admin_update")
	@Override
	public void checkAdminUpdate() {
		// TODO Auto-generated method stub
		
	}
	@RequiresPermissions("sys_admin_view")
	@Override
	public void checkAdminQuery() {
		// TODO Auto-generated method stub
		
	}

}
