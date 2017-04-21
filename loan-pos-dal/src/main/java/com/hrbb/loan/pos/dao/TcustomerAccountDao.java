package com.hrbb.loan.pos.dao;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TcustomerAccount;

public interface TcustomerAccountDao {
    int deleteByPrimaryKey(String accountNo);

    int insert(TcustomerAccount record);

    int insertSelective(TcustomerAccount record);

    TcustomerAccount selectByPrimaryKey(String accountNo);

    int updateByPrimaryKeySelective(TcustomerAccount record);

    int updateByPrimaryKey(TcustomerAccount record);
    
    Map<String,Object> getCustomerAccountInfoByCustId(String custId);

   	int updateCustomerAccount(Map<String, Object> bankAccNoMap);

   	int insertCustomerAccount(Map<String, Object> bankAccNoMap);

	List<Map<String, Object>> getAllInnerAccountList(Map<String, Object> accno);

	long countAccountNumber(Map<String, Object> accno);
}