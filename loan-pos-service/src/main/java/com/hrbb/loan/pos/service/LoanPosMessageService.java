/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.service;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TMessage;
import com.hrbb.loan.pos.dao.entity.TMessageHist;

/**
 * 
 * @author byp
 * @version $Id: LoanPosMessageService.java, v 0.1 2015年3月17日 下午3:29:44 byp Exp $
 */
public interface LoanPosMessageService {
    
    public int deleteById(Integer id);

    public int insert(TMessage record);
    
    public List<TMessage> selectAll();
    
    public void insertMessageHist(TMessageHist record);
    
    public List<TMessage> selectByMap(Map map);

    public int updateCount(Map<String, Object> map);
 
}
