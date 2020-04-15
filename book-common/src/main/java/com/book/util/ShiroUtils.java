package com.book.util;

import org.apache.shiro.SecurityUtils;


public class ShiroUtils {

	public static String getUserName() {
		String username= getUser().toString();
		int indexOf =username.indexOf(", name=");
		username=username.substring(indexOf+7);
		int indexOf2 = username.indexOf(", ");
		username=username.substring(0,indexOf2);
		return  username;
	}

	public static Object getUser() {
		return  SecurityUtils.getSubject().getPrincipal();
	}
}
