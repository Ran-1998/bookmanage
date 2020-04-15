package com.book.serviceImpl;

import com.book.dao.VerifyDao;
import com.book.service.VerifyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyServiceImpl implements VerifyService {

	@Autowired
    private VerifyDao verifyDao;

    /** 添加验证码 */
    public void addVerifyCode(String addVerify){
        verifyDao.addVerifyCode(addVerify);
    }

    /** 查询验证码
     * @return*/
    public int queryVerifyCode(String queryVerify){
        int verifyCount = verifyDao.queryVerifyCode(queryVerify);
        return verifyCount;
    }

    /** 删除验证码 */
    public void deleteVerifyCode(String deleteVerify){
        verifyDao.deleteVerifyCode(deleteVerify);
    }

    /** 添加电话号码 */
    public void addTel(String addtel){
        verifyDao.addTel(addtel);
    }

    /** 添加电话号码 */
    public int queryTel(String querytel){
        int telCount = verifyDao.queryTel(querytel);
        return telCount;
    }

}