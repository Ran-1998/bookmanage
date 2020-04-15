package com.book.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.javassist.expr.NewArray;

import lombok.Data;

/**
 * @author Ran
 * @date 2020年3月28日
 */
public class DateUtil {
	
	//标准时间字符转日期
	public static Date StringtoDate(String date) {
		SimpleDateFormat simdate=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
		try {
			Date parse = simdate.parse(date);
			return parse;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//加天数
	public static Date addDate(Date date,long day)  {
		 long time = date.getTime(); // 得到指定日期的毫秒数
		 day = day*24*60*60*1000; // 要加上的天数转换成毫秒数
		 time+=day; // 相加得到新的毫秒数
		 return new Date(time); // 将毫秒数转换成日期
		}
	
	//比较大小
	public static boolean compareDate(Date data1,Date date22) {
		
		SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//创建日期转换对象：年月日 时分秒
		String date1 = sdf.format(data1); //假设 设定日期是 2018-11-11 11:11:11
		String date2 = sdf.format(date22); //假设 设定日期是 2018-11-11 11:11:11
		try {
		    Date dateD = sdf.parse(date1);    //转换为 date 类型 Debug：Sun Nov 11 11:11:11 CST 2018
		    Date dateE = sdf.parse(date2);    //转换为 date 类型 Debug：Sun Nov 11 11:11:11 CST 2018
		    boolean flag = dateD.getTime() >= dateE.getTime();
		   // System.err.println("flag = "+flag);  // flag = false
		    return flag;
		} catch (ParseException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		return false; 		
	}
}
