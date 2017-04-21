/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hrbb.loan.pos.dao.TPaybackRunningRecordDao;
import com.hrbb.loan.pos.dao.entity.TPaybackRunningRecord;
import com.hrbb.loan.pos.service.PaybackRunningRecordService;

/**
 * 
 * @author marco
 * @version $Id: TPaybackRunningRecordServiceImpl.java, v 0.1 2015-4-26 下午2:42:48 marco Exp $
 */
@Service("paybackRunningRecordService")
public class PaybackRunningRecordServiceImpl implements
		PaybackRunningRecordService {

	@Autowired
	@Qualifier("tPaybackRunningRecordDao")
	private TPaybackRunningRecordDao tPaybackRunningRecordDao;
	
	/** 
	 * @see com.hrbb.loan.pos.service.TPaybackRunningRecordService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String paybackRunningRecordId) {
		return 0;
	}

	/** 
	 * @see com.hrbb.loan.pos.service.TPaybackRunningRecordService#insert(com.hrbb.loan.pos.dao.entity.TPaybackRunningRecord)
	 */
	@Override
	public int insert(TPaybackRunningRecord record) {
		return 0;
	}

	/** 
	 * @see com.hrbb.loan.pos.service.TPaybackRunningRecordService#insertSelective(com.hrbb.loan.pos.dao.entity.TPaybackRunningRecord)
	 */
	@Override
	public int insertSelective(TPaybackRunningRecord record) {
		return 0;
	}

	/** 
	 * @see com.hrbb.loan.pos.service.TPaybackRunningRecordService#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public TPaybackRunningRecord selectByPrimaryKey(
			String paybackRunningRecordId) {
		return tPaybackRunningRecordDao.selectByPrimaryKey(paybackRunningRecordId);
	}

	/** 
	 * @see com.hrbb.loan.pos.service.TPaybackRunningRecordService#selectRunningRecord()
	 */
	@Override
	public List<TPaybackRunningRecord> selectRecordInfoHB17() {
		return tPaybackRunningRecordDao.selectRecordInfoHB17();
	}

	/** 
	 * @see com.hrbb.loan.pos.service.TPaybackRunningRecordService#updateByPrimaryKeySelective(com.hrbb.loan.pos.dao.entity.TPaybackRunningRecord)
	 */
	@Override
	public int updateByPrimaryKeySelective(TPaybackRunningRecord record) {
		return 0;
	}

	/** 
	 * @see com.hrbb.loan.pos.service.TPaybackRunningRecordService#updateByPrimaryKey(com.hrbb.loan.pos.dao.entity.TPaybackRunningRecord)
	 */
	@Override
	public int updateByPrimaryKey(TPaybackRunningRecord record) {
		return 0;
	}

}
