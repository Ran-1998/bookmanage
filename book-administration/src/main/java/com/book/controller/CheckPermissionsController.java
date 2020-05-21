package com.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.service.CheckPermissions;

/**
 * @author Ran
 * @date 2020年5月21日
 */
@RestController
@RequestMapping("/check")
public class CheckPermissionsController {
@Autowired
CheckPermissions checkPermissions;
@RequestMapping("/bookquery")
public void checkBookQuery() {
	checkPermissions.checkBookQuery();
}
@RequestMapping("/bookadd")
public void checkBookAdd() {
	checkPermissions.checkBookAdd();
}
@RequestMapping("/bookdelete")
public void checkBookDelete() {
	checkPermissions.checkBookDelete();
}
@RequestMapping("/bookupdate")
public void checkBookUpdate() {
	checkPermissions.checkBookUpadte();
}
@RequestMapping("/userupdate")
public void checkUserUpdate() {
	checkPermissions.checkUserUpdate();
}
@RequestMapping("/userquery")
public void checkUserQuery() {
	checkPermissions.checkUserQuery();
}
@RequestMapping("/userdelete")
public void checkUserDelete() {
	checkPermissions.checkUserDelete();
}
@RequestMapping("/useradd")
public void checkUserAdd() {
	checkPermissions.checkUserAdd();
}

@RequestMapping("/adminadd")
public void checkAdminAdd() {
	checkPermissions.checkAdminAdd();
}
@RequestMapping("/admindelete")
public void checkAdminDelete() {
	checkPermissions.checkAdminDelete();
}
@RequestMapping("/adminupdate")
public void checkAdminUpdate() {
	checkPermissions.checkAdminUpdate();
}
@RequestMapping("/adminquery")
public void checkAdminQuery() {
	checkPermissions.checkAdminQuery();
}
}
