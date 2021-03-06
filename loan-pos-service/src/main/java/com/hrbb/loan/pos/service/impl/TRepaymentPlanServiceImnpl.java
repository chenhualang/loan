/**
 * 
 */
package com.hrbb.loan.pos.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrbb.loan.pos.dao.TRepaymentPlanDao;
import com.hrbb.loan.pos.dao.entity.TRepaymentPlan;
import com.hrbb.loan.pos.service.TRepaymentPlanService;

/**
 * <p>Title: TRepaymentPlanServiceImnpl.java </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
 *     
 * @author linzhaolin@hrbb.com.cn
 * @version 
 * @date 2015年7月30日
 *
 * logs: 1. 
 */
@Service("repaymentPlanService")
public class TRepaymentPlanServiceImnpl implements TRepaymentPlanService {
	
	@Autowired
	private TRepaymentPlanDao tRepaymentPlanDao;
	
	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.service.TRepaymentPlanService#insert(com.hrbb.loan.pos.dao.entity.TRepaymentPlan)
	 */
	@Override
	public int insert(TRepaymentPlan record) {
		return tRepaymentPlanDao.insert(record);
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.service.TRepaymentPlanService#updateSelective(com.hrbb.loan.pos.dao.entity.TRepaymentPlan)
	 */
	@Override
	public int updateSelective(TRepaymentPlan record) {
		return tRepaymentPlanDao.updateSelective(record);
	}

	@Override
	public int insertSelective(TRepaymentPlan record) {
		return tRepaymentPlanDao.insertSelective(record);
	}

	@Override
	public TRepaymentPlan getRecentlyPlan(String receiptId) {
		return tRepaymentPlanDao.getRecentlyPlan(receiptId);
	}

	@Override
	public List<TRepaymentPlan> getAllPlan(String receiptId) {
		// TODO Auto-generated method stub
		return tRepaymentPlanDao.getAllPlan(receiptId);
	}

	@Override
	public List<TRepaymentPlan> getAllPlan(String receiptId, Date dateFrom) {
		// TODO Auto-generated method stub
		return tRepaymentPlanDao.getRepaymentPlans(receiptId, dateFrom);
	}

    @Override
    public TRepaymentPlan getPlanByReceiptIdAndPeriod(String receiptId, String period) {
        return tRepaymentPlanDao.getPlanByReceiptIdAndPeriod(receiptId,period);
    }

    @Override
    public List<TRepaymentPlan> getAllPlanByReceiptId(String receiptId) {
        return tRepaymentPlanDao.getAllPlanByReceiptId(receiptId);
    }

    @Override
    public int removeRepaymentPlan(String receiptId) {
        return tRepaymentPlanDao.deleteByReceiptId(receiptId);
    }

    @Override
    public Long getPlanCountByReceiptId(String receiptId) {
        return tRepaymentPlanDao.countRepaymentPlansByReceiptId(receiptId);
    }

    @Override
    public List<TRepaymentPlan> getPlansByReceiptIdInPage(Map<String, Object> reqMap) {
        return tRepaymentPlanDao.queryRepaymentPlanByReceiptIdInPage(reqMap);
    }

    @Override
    public Map<String, Object> getBatchRecentRepayPlan(String receiptId) {
        return tRepaymentPlanDao.getBatchRecentRepayPlan(receiptId);
    }
	

}
