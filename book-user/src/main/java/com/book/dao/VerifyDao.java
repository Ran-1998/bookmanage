package com.book.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VerifyDao {

    /** 获得验证码 */
    public void addVerifyCode(String addVerify);

    /** 查询验证码 */
    public int queryVerifyCode(String queryVerify);

    /** 删除验证码 */
    public void deleteVerifyCode(String deleteVerify);

    /** 添加电话号码 */
    public void addTel(String addtel);

    /** 查询电话号码 */
    public int queryTel(String querytel);
}