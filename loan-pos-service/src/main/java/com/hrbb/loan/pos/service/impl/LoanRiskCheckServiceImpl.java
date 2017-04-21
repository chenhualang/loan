/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hrbb.loan.pos.dao.TBlacklistDao;
import com.hrbb.loan.pos.dao.TCreditApplyForReviewDao;
import com.hrbb.loan.pos.dao.TCreditReportIdentityDao;
import com.hrbb.loan.pos.dao.TCreditReportProfessionDao;
import com.hrbb.loan.pos.dao.TCustomerDao;
import com.hrbb.loan.pos.dao.TPosSerialnoInfoDao;
import com.hrbb.loan.pos.dao.TRiskCheckCalcIndexDao;
import com.hrbb.loan.pos.dao.TRiskCheckModelIndexDao;
import com.hrbb.loan.pos.dao.TRiskCheckResultDao;
import com.hrbb.loan.pos.dao.entity.TCreditApply;
import com.hrbb.loan.pos.dao.entity.TCreditReportIdentity;
import com.hrbb.loan.pos.dao.entity.TCreditReportProfession;
import com.hrbb.loan.pos.dao.entity.TCustomer;
import com.hrbb.loan.pos.dao.entity.TRiskCheckCalcIndex;
import com.hrbb.loan.pos.dao.entity.TRiskCheckModelIndex;
import com.hrbb.loan.pos.dao.entity.TRiskCheckResult;
import com.hrbb.loan.pos.service.LoanRiskCheckService;
import com.hrbb.loan.pos.util.DateUtil;
import com.hrbb.loan.pos.util.StringUtil;
import com.hrbb.loan.pos.util.constants.ReviewNoteConstants;

/**
 * 
 * @author XLY
 * @version $Id: TRiskCheckServiceImpl.java, v 0.1 2015-3-11 上午9:43:28 XLY Exp $
 */
@Service("loanRiskCheckService")
public class LoanRiskCheckServiceImpl implements LoanRiskCheckService {

	private Logger LOG = LoggerFactory
			.getLogger(LoanRiskCheckServiceImpl.class);

	@Autowired
	private TRiskCheckResultDao tRiskCheckResultDao;

	@Autowired
	private TRiskCheckCalcIndexDao tRiskCheckCalcIndexDao;

	@Autowired
	private TRiskCheckModelIndexDao tRiskCheckModelIndexDao;

	@Autowired
	@Qualifier("tCreditApplyForReviewDao")
	private TCreditApplyForReviewDao dao;

	@Autowired
	private TBlacklistDao daoBL;

	@Autowired
	@Qualifier("tRiskCheckCalcIndexDao")
	private TRiskCheckCalcIndexDao daoCCI;

	@Autowired
	private TCreditReportIdentityDao tCreditReportIdentityDao;

	@Autowired
	private TCreditReportProfessionDao tCreditReportProfessionDao;

	@Autowired
	private TPosSerialnoInfoDao tPosSerialnoInfoDao;

	@Autowired
	private TCustomerDao tCustomerDao;

	@Override
	public TRiskCheckResult selectRiskCheckResult(String loanId) {
		TRiskCheckResult r = tRiskCheckResultDao.selectSelective(loanId);
		if (r == null) {
			return new TRiskCheckResult();
		}
		// 建议尽职调查策略
		if (ReviewNoteConstants.Y.equals(r.getResult11())) {
			r.setResult11(ReviewNoteConstants.SUGGEST_DILIGENCE_Y);
		} else {
			r.setResult11(ReviewNoteConstants.SUGGEST_DILIGENCE_N);
		}
		// 与申请人手机是否一致
		if (ReviewNoteConstants.Y.equals(r.getResult14())) {
			r.setResult14_Name(ReviewNoteConstants.MATCH_Y);
		} else if(StringUtil.isNotEmpty(r.getResult14())) {
			r.setResult14_Name(ReviewNoteConstants.MATCH_N);
		} else {
		    r.setResult14_Name(ReviewNoteConstants.MATCH_NULL);
        }
		return r;
	}

	@Override
	public int deleteRiskCheckResult(String loanId) {
		return tRiskCheckResultDao.deleteByPrimaryKey(loanId);
	}

	@Override
	public int insertRiskCheckResult(TRiskCheckResult record) {
		return tRiskCheckResultDao.insertSelective(record);
	}

	@Override
	public TRiskCheckCalcIndex selectRiskCalcIndex(String loanId) {
		return tRiskCheckCalcIndexDao.selectByPrimaryKey(loanId);
	}

	@Override
	public int deleteRiskCalcIndex(String loanId) {
		return tRiskCheckCalcIndexDao.deleteByPrimaryKey(loanId);
	}

	@Override
	public int insertRiskCalcIndex(TRiskCheckCalcIndex record) {
		return tRiskCheckCalcIndexDao.insertSelective(record);
	}

	@Override
	public TRiskCheckModelIndex selectRiskModelIndex(String loanId) {
		return tRiskCheckModelIndexDao.selectByPrimaryKey(loanId);
	}

	@Override
	public int deleteRiskModelIndex(String loanId) {
		return tRiskCheckModelIndexDao.deleteByPrimaryKey(loanId);
	}

	@Override
	public int insertRiskModelIndex(TRiskCheckModelIndex record) {
		return tRiskCheckModelIndexDao.insertSelective(record);
	}

	/**
	 * 风险预测
	 * 
	 * @see com.hrbb.loan.pos.service.ReviewNoteService#selectForRiskDetection()
	 */
	// @Override
	public List<Map<String, Object>> selectForRiskDetection(String loanid) {

		List<Map<String, Object>> l = Lists.newArrayList();
		try{
			
	

		Map<String, Object> m = dao.selectForRiskDetection(loanid);
		

		String applyStatus =  Objects.firstNonNull(m.get("applyStatus"), "").toString(); // 通过申请状态过滤显示的规则范围
															// add by
															// Lin,Zhaolin
		Map<String, Object> mRezult = Maps.newHashMap();
		// 1申请人是否企业法人
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_1);
		String custName = Objects.firstNonNull(m.get(
				ReviewNoteConstants.RISKDETECTIO_MAP_KEY_CUSTNAME), "").toString();
		String legalPersonName = m
				.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_LEGALPERSONNAME) == null ? ""
				: m.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_LEGALPERSONNAME).toString()
						;
		
		// 缺少企业法人信息
		if (StringUtil.isEmpty(legalPersonName)) {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_1_NULL);
		} else if (custName.equals(legalPersonName)) {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
		} else {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_1);
		}
		l.add(mRezult);
		// 2企业经营年限是否符合准入
		mRezult = Maps.newHashMap();
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_2);
		String channel = Objects.firstNonNull(m
				.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_CHANNEL), "").toString()
				;
		// 缺少企业经营年限信息
		if (m.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_REGISTDATE) == null) {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_2_NULL);
		} else {
			Date registDate = null;
			try {
				if(m.get(
						ReviewNoteConstants.RISKDETECTIO_MAP_KEY_REGISTDATE) != null){
					registDate = DateUtil.getDatePattern3(Objects.firstNonNull(m.get(
							ReviewNoteConstants.RISKDETECTIO_MAP_KEY_REGISTDATE), "").toString()
							);
					
				}
				// 企业成立月数
				long diff = DateUtil.getDateDiffFromToday(registDate) / 30;
				// 银联
				if (ReviewNoteConstants.CHANNEL_CODE_UP.equals(channel)) {
					// 企业成立小于18个月
					if (diff < 18) {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
								ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_2);
					} else {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
					}
					// 商圈
				} else if (ReviewNoteConstants.CHANNEL_CODE_SQ.equals(channel)) {
					// 企业成立小于24个月
					if (diff < 24) {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
								ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_2);
					} else {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
					}
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
			} catch (Exception e) {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
				mRezult.put(
						ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_2_NULL);
			}
		}
		l.add(mRezult);
		// 3是否重复申请
		mRezult = Maps.newHashMap();
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_3);
		String paperId = Objects.firstNonNull(m
				.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_PAPERID), "").toString()
				;

		String matePaperId = null;
		if (m.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_MATEPAPERID) != null) {
			matePaperId = Objects.firstNonNull(m.get(
					ReviewNoteConstants.RISKDETECTIO_MAP_KEY_MATEPAPERID), "").toString()
					;
		}

		String regiCode = Objects.firstNonNull(m
				.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_REGICODE) == null ? null
				: m.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_REGICODE), "").toString();
						;
		// 该笔申请开始时间
		String beginDateNow = m
				.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_BEGINDATE) == null ? null
				: m.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_BEGINDATE).toString()
						;

		Map<String, Object> mPramater = Maps.newHashMap();
		mPramater
				.put(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_PAPERID, paperId);
		mPramater.put(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_LOAN_ID, loanid);
		mPramater.put(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_BEGINDATE,
				beginDateNow);
		List<TCreditApply> caList = dao.selectHistory(mPramater);
		String notice = ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_3;
		int times = caList.size();
		// 最近一次申请的申请编号
		String loanIDLatest = null;
		// 申请人
		if (times > 0) {
			TCreditApply ca = caList.get(0);
			// 最近一次申请的申请编号
			loanIDLatest = ca.getLoanId();

			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
			String beginDate = DateUtil.getDateToString3(ca.getBeginDate());
			mRezult.put(
					ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					notice.replaceFirst(
							ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
							ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_3_1)
							.replaceFirst(
									ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
									String.valueOf(times + 1))
							.replaceFirst(
									ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
									beginDate)
							.replaceFirst(
									ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
									ca.getApplyStatusName()));
			// 申请人配偶
		} else {
			// 申请人配偶不存在
			if (StringUtil.isEmpty(matePaperId)) {
				// 营业执照
				checkRegiCode(loanid, regiCode, beginDateNow, mRezult);
				// 申请人配偶存在
			} else {
				mPramater.clear();
				mPramater.put(
						ReviewNoteConstants.RISKDETECTIO_MAP_KEY_MATEPAPERID,
						matePaperId);
				mPramater.put(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_LOAN_ID,
						loanid);
				mPramater.put(
						ReviewNoteConstants.RISKDETECTIO_MAP_KEY_BEGINDATE,
						beginDateNow);
				caList = dao.selectHistory(mPramater);
				times = caList.size();
				if (times > 0) {
					TCreditApply ca = caList.get(0);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					String beginDate = DateUtil.getDateToString3(ca
							.getBeginDate());
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							notice.replaceFirst(
									ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
									ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_3_2)
									.replaceFirst(
											ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
											String.valueOf(times + 1))
									.replaceFirst(
											ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
											beginDate)
									.replaceFirst(
											ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
											ca.getApplyStatusName()));
					// 营业执照
				} else {
					checkRegiCode(loanid, regiCode, beginDateNow, mRezult);
				}
			}
		}
		l.add(mRezult);
		// 判断是否续贷
		mRezult = Maps.newHashMap();
		mRezult.put(
				ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_3_CONTINUELENDING);
		// 存在上笔申请
		if (StringUtil.isNotEmpty(loanIDLatest)) {
			if (dao.selectContinueLending(loanIDLatest) > 0) {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
				mRezult.put(
						ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_3_CONTINUELENDING_0);
			} else {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				mRezult.put(
						ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_3_CONTINUELENDING_1);
			}
		} else {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
			mRezult.put(
					ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_3_CONTINUELENDING_1);
		}
		l.add(mRezult);
		// 4电话号码多人使用
		mRezult = Maps.newHashMap();
		mPramater = Maps.newHashMap();
		String mobilePhone =  Objects.firstNonNull(m.get(
				ReviewNoteConstants.RISKDETECTIO_MAP_KEY_MOBILEPHONE), "").toString();
				;
		mPramater.put(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_MOBILEPHONE,
				mobilePhone);
		mPramater.put(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_LOAN_ID, loanid);
		caList = dao.selectHistory(mPramater);
		times = caList.size();
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_4);
		// 办公电话
		String tel = Objects.firstNonNull(m.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_TEL), "").toString();
		if (times > 0) {
			TCreditApply caTemp = caList.get(0);
			String noticeTemp = ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_4_1
					.replaceFirst(
							ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
							caTemp.getCustName());
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					noticeTemp);
		} else {
			mPramater.clear();
			mPramater.put(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_TEL, tel);
			mPramater.put(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_LOAN_ID,
					loanid);
			caList = dao.selectHistory(mPramater);
			times = caList.size();
			if (times > 0) {
				TCreditApply caTemp = caList.get(0);
				String noticeTemp = ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_4_2
						.replaceFirst(
								ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
								caTemp.getCustName());
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						noticeTemp);
			} else {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
			}
		}
		l.add(mRezult);
		// 5疑似限入行业
		mRezult = Maps.newHashMap();
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_5);
		// 互金行业分类(内部)
		String industryTypeId2 = null;
		if (m.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_INDUSTRYTYPEID2) != null) {
			industryTypeId2 = Objects.firstNonNull(m.get(
					ReviewNoteConstants.RISKDETECTIO_MAP_KEY_INDUSTRYTYPEID2), "").toString();
					;
		}

		boolean flag5 = true;
		// 银联
		if (ReviewNoteConstants.CHANNEL_CODE_UP.equals(channel)) {
			// 行内行业为建材行业的客户
			if (ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_01
					.equals(industryTypeId2)) {
				flag5 = false;
			}
		}
		if (flag5) {
			// 互金行业分类(国标)
			String industryTypeId = null;
			if (m.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_INDUSTRYTYPEID) != null) {
				industryTypeId = Objects.firstNonNull(m
						.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_INDUSTRYTYPEID), "").toString()
						;
			}
			if (StringUtil.isNotEmpty(industryTypeId)
					&& industryTypeId
							.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_INDUSTRYTYPEID_K) >= 0) {
				flag5 = false;
			}
		}
		if (flag5) {
			// 行内行业为以下任一：古董、建筑和工厂；
			if (ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_11
					.equals(industryTypeId2)
					|| ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_12
							.equals(industryTypeId2)
					|| ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_13
							.equals(industryTypeId2)) {
				flag5 = false;
			}
		}

		if (flag5) {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
		} else {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_5);
		}
		l.add(mRezult);
		// 6限制性商户校验
		mRezult = Maps.newHashMap();
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_6);
		// 经营地址
		String posCustAddress = Objects.firstNonNull(m.get(
				ReviewNoteConstants.RISKDETECTIO_MAP_KEY_POSCUSTADDRESS), "").toString()
				;
		// 商户名称
		String posCustName = Objects.firstNonNull(m.get(
				ReviewNoteConstants.RISKDETECTIO_MAP_KEY_POSCUSTNAME), "").toString()
				;
		// 不包含我行慎贷客户
		if (posCustAddress
				.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTADDRESS_1) < 0
				&& posCustAddress
						.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTADDRESS_2) < 0
				&& posCustAddress
						.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTADDRESS_3) < 0) {
			if (posCustName
					.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTNAME_1) < 0) {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
			} else {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_6);
			}
		} else {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_6);
		}
		l.add(mRezult);
		// 7限制性行业校验
		mRezult = Maps.newHashMap();
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_7);
		// 商户名称包含以下字样：石材、石板、石料、大理石、水泥、铝合金、涂料、管材、钢材
		if (posCustName
				.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTNAME_2) >= 0
				|| posCustName
						.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTNAME_3) >= 0
				|| posCustName
						.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTNAME_4) >= 0
				|| posCustName
						.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTNAME_5) >= 0
				|| posCustName
						.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTNAME_6) >= 0
				|| posCustName
						.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTNAME_7) >= 0
				|| posCustName
						.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTNAME_8) >= 0
				|| posCustName
						.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTNAME_9) >= 0
				|| posCustName
						.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTNAME_10) >= 0) {

			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_7);
		} else {
			// 放在下面做
			// 行内行业分类
			// if (StringUtil.isEmpty(industryTypeId2)) {
			// mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
			// ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
			// mRezult.put(
			// ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
			// ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_7_NULL);
			// } else {
			// if (industryTypeId2
			// .equals(ReviewNoteConstants.RISKDETECTIO_CHECK_INDUSTRYTYPEID2_04))
			// {
			// mRezult.put(
			// ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
			// ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
			// mRezult.put(
			// ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
			// ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_7);
			// } else {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
			// }
			// }
		}
		l.add(mRezult);

		// 区划
		String operAddrCode = Objects.firstNonNull(m.get(
				ReviewNoteConstants.RISKDETECTIO_MAP_KEY_OPERADDRCODE), "").toString()
				;

		// 8江浙银联建材校验
		mRezult = Maps.newHashMap();
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_8);
		// 行内行业分类
		if (StringUtil.isEmpty(industryTypeId2)) {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_7_NULL);
		} else {
			// 银联
			if (ReviewNoteConstants.CHANNEL_CODE_UP.equals(channel)
					&& industryTypeId2
							.equals(ReviewNoteConstants.RISKDETECTIO_CHECK_INDUSTRYTYPEID2_01)
					&& (operAddrCode
							.equals(ReviewNoteConstants.RISKDETECTIO_CHECK_OPERADDRCODE_32) || operAddrCode
							.equals(ReviewNoteConstants.RISKDETECTIO_CHECK_OPERADDRCODE_33))) {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_8);
			} else {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
			}
		}
		l.add(mRezult);
		// 9泰顺建材
		mRezult = Maps.newHashMap();
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_9);
		// 行内行业分类
		if (StringUtil.isEmpty(industryTypeId2)) {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_7_NULL);
		} else {
			if (industryTypeId2
					.equals(ReviewNoteConstants.RISKDETECTIO_CHECK_INDUSTRYTYPEID2_01)
					&& paperId
							.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_PAPERKIND) == 0) {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_9);
			} else {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
			}
		}
		l.add(mRezult);

		// 产品代码
		String prodId = Objects.firstNonNull(m.get("prodId"), "").toString();

		if (!applyStatus.matches("(10|20|21)")) { // 受理和资料审核和资料审核补件不显示模型部分探测结果
			// 10-23
			// 风险初审结果查询
			TRiskCheckCalcIndex cci = daoCCI.selectByPrimaryKey(loanid);

			if (cci != null) {
				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_10);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex31())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_31);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_11);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex08())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_08);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_12);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex09())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_09);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_13);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex10())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_10);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_14);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex11())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_11);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_15);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex12())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_12);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_16);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex13())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_13);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_17);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex05())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_05);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_18);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex14())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_14);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_19);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex15())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_15);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_20);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex16())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_16);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_21);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex17())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_17);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_22);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex18())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_18);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_23);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex19())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_19);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				// 25-29
				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_25);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex24())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_24);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);

				// pos贷
				if (ReviewNoteConstants.POS_LOAN_ID.equals(prodId)) {

					mRezult = Maps.newHashMap();
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_26);
					if (ReviewNoteConstants.Y.equals(cci.getCalcIndex20())) {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
						if (cci.getCalcIndex20_index() != null) {
							switch (cci.getCalcIndex20_index()) {
							case 1:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_20_1);
								break;
							case 2:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_20_2);
								break;
							case 3:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_20_3);
								break;
							case 4:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_20_4);
								break;
							case 5:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_20_5);
								break;
							case 6:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_20_6);
								break;
							default:
								break;
							}
						}
					} else {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
					}
					l.add(mRezult);

					mRezult = Maps.newHashMap();
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_27);
					if (ReviewNoteConstants.Y.equals(cci.getCalcIndex21())) {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
						if (cci.getCalcIndex21_index() != null) {
							switch (cci.getCalcIndex21_index()) {
							case 1:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_20_1);
								break;
							case 2:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_20_2);
								break;
							case 3:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_20_3);
								break;
							default:
								break;
							}
						}
					} else {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
					}
					l.add(mRezult);

					mRezult = Maps.newHashMap();
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_28);
					if (ReviewNoteConstants.Y.equals(cci.getCalcIndex22())) {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
								ReviewNoteConstants.CALC_INDEX_MSG_22);
					} else {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
					}
					l.add(mRezult);

					mRezult = Maps.newHashMap();
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_29);
					if (ReviewNoteConstants.Y.equals(cci.getCalcIndex23())) {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
						if (cci.getCalcIndex23_index() != null) {
							switch (cci.getCalcIndex23_index()) {
							case 1:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_23_1);
								break;
							case 2:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_23_2);
								break;
							case 3:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_23_3);
								break;
							case 4:
								mRezult.put(
										ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
										ReviewNoteConstants.CALC_INDEX_MSG_23_4);
								break;
							default:
								break;
							}
						}
					} else {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
					}
					l.add(mRezult);
				}

				// 31-32
				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_31);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex28())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_28);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);
				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_32);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex29())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_29);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);
				// 35信用卡逾期情况
				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_35);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex32())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_32);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);
				// 38征信负债情况探测
				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_38);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex33())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_33);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);
				// 39征信查询次数探测
				mRezult = Maps.newHashMap();
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_39);
				if (ReviewNoteConstants.Y.equals(cci.getCalcIndex34())) {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
							ReviewNoteConstants.CALC_INDEX_MSG_34);
				} else {
					mRezult.put(
							ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
							ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				}
				l.add(mRezult);
			}
		}

		// 24申请人电话校验
		mRezult = Maps.newHashMap();
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_24);
		String mateMobilephone = null;
		if (m.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_MATEMOBILEPHONE) != null) {
			mateMobilephone = Objects.firstNonNull(m.get(
					ReviewNoteConstants.RISKDETECTIO_MAP_KEY_MATEMOBILEPHONE), "").toString()
					;
		}
		if (mobilePhone.equals(mateMobilephone)) {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_24);
		} else {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
		}
		l.add(mRezult);

		// 30申请人黑名单校验
		mRezult = Maps.newHashMap();
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_30);

		List<String> infoDetails = new ArrayList<>();
		infoDetails.add(paperId);
		infoDetails.add(mobilePhone);
		infoDetails.add(tel);
		infoDetails.add(Objects.firstNonNull(m.get(
				ReviewNoteConstants.RISKDETECTIO_MAP_KEY_RESIDENTDETAIL), "").toString());
		infoDetails.add(posCustName);
		infoDetails.add(posCustAddress);

		if (!applyStatus.matches("(10|20|21)")) { // 受理和资料审核和资料审核补件不显示模型部分探测结果
			// 查询征信报告身份信息
			TCreditReportIdentity cri = selectCreditReportIdentity(paperId,
					infoDetails);
			if (cri != null) {
				// 查询征信报告职业信息
				// selectCreditReportProfession(cri.getReportNo(), infoDetails);
				selectCreditReportProfession(cri.getQueryId(), infoDetails);
			}
		}
		// 申请人黑名单校验
		long count = daoBL.selectForRiskCheck(infoDetails);
		if (count == 0) {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
		} else {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_30);
		}
		l.add(mRezult);

		// 34从业年限探测
		mRezult = Maps.newHashMap();
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_34);
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);

		String busiYear = Objects.firstNonNull(m.get(
				ReviewNoteConstants.RISKDETECTIO_MAP_KEY_BUSIYEAR), "").toString();

		// 申请人从业年限<5
		// 行内行业分类 in (‘生产行业’,’制造业’)
		if (Integer.parseInt(busiYear) < 5
				&& (ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_04
						.equals(industryTypeId2) || posCustName
						.indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTNAME_11) >= 0)) {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_34);
		} else {
			// 申请人出生年月
			if (m.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_BIRTDATE) == null) {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_34);
			} else {
				// 行内行业分类 in (’建材’,‘茶叶’)
				if ((ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_01
						.equals(industryTypeId2) || ReviewNoteConstants.RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_07
						.equals(industryTypeId2))) {
					// 出生年
					if (m.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_BIRTDATE) == null) {
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
								ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
						mRezult.put(
								ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
								ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_34);
					} else {
						// 出生年
						String birtDateYear = String.valueOf(m
								.get(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_BIRTDATE)).substring(0, 4);
						// 申请人从业年限<3
						if (Integer.parseInt(busiYear) < 3
								|| Integer.parseInt(birtDateYear) >= 1990) {
							mRezult.put(
									ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
									ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
							mRezult.put(
									ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
									ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_34);
						}
					}
				}
			}
		}
		l.add(mRezult);

		// 36POS流水准入判断
		// POS流水准入判断
		int result = checkPosSerialno(loanid);
		// 没有pos流水
		if (result == -1) {
			// 没有pos流水不做处理

			// 有pos流水
		} else {
			mRezult = Maps.newHashMap();
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_36);
			// 满足准入条件
			if (result == 0) {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
				// POS流水不满足准入条件
			} else {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_36);
			}
			l.add(mRezult);
		}

		// 37疑似地区限入
		mRezult = Maps.newHashMap();
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_37);
		// 58金融不受该限制
		// 1.产品=“流量贷”；
		// if (!ReviewNoteConstants.CHANNEL_CODE_58.equals(channel)
		// && ReviewNoteConstants.FLOW_LOAN_ID.equals(prodId)) {
		// //
		// 2.经营地在以下任一地区：湖南43****、海南46****、云南53****、贵州52****、甘肃62****、青海63****、宁夏64****。
		// if (operAddrCode
		// .indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_OPERADDRCODE_43) == 0
		// || operAddrCode
		// .indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_OPERADDRCODE_46) == 0
		// || operAddrCode
		// .indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_OPERADDRCODE_52) == 0
		// || operAddrCode
		// .indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_OPERADDRCODE_53) == 0
		// || operAddrCode
		// .indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_OPERADDRCODE_62) == 0
		// || operAddrCode
		// .indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_OPERADDRCODE_63) == 0
		// || operAddrCode
		// .indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_OPERADDRCODE_64) ==
		// 0) {
		//
		// mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
		// ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
		// mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
		// ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_37);
		// } else {
		// // 中网从云南进的件，可以不拦截
		// if (!ReviewNoteConstants.CHANNEL_CODE_ZW.equals(channel)
		// && operAddrCode
		// .indexOf(ReviewNoteConstants.RISKDETECTIO_CHECK_OPERADDRCODE_53) ==
		// 0) {
		// mRezult.put(
		// ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
		// ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_9);
		// mRezult.put(
		// ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
		// ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_37);
		// } else {
		// mRezult.put(
		// ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
		// ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
		// }
		// }
		// } else {
		mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
				ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
		// }
		l.add(mRezult);

		// 有配偶信息时
		if (StringUtil.isNotEmpty(matePaperId)) {
			// 40配偶信息重复性校验
			mRezult = Maps.newHashMap();
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_40);

			TCustomer custPramater = new TCustomer();
			custPramater.setCheckFlag(40);
			custPramater.setPaperId(paperId);
			custPramater.setMatePaperId(matePaperId);
			// 查询
			long countCust = tCustomerDao.selectCountForRiskCheck(custPramater);
			// 申请人配偶证件号与其他申请人配偶的证件重复；
			if (countCust > 0) {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_40);
			} else {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
			}
			l.add(mRezult);

			// 41配偶信息一致性校验
			mRezult = Maps.newHashMap();
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_41);

			custPramater.setCheckFlag(41);
			// custPramater.setMatePaperId(matePaperId);
			custPramater.setFamilyCustName(Objects.firstNonNull(m.get(
					ReviewNoteConstants.RISKDETECTIO_MAP_KEY_FAMILYCUSTNAME), "").toString());
			// 查询
			countCust = tCustomerDao.selectCountForRiskCheck(custPramater);
			// 申请人配偶证件号与其他申请人证件号相同，但姓名不相同；
			if (countCust > 0) {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_41);
			} else {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
			}
			l.add(mRezult);

			// 42配偶电话重复性校验
			mRezult = Maps.newHashMap();
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKINFOR_42);

			custPramater.setCheckFlag(42);
			// custPramater.setPaperId(paperId);
			custPramater.setMateMobilephone(Objects.firstNonNull(m.get(
					ReviewNoteConstants.RISKDETECTIO_MAP_KEY_MATEMOBILEPHONE), "").toString()
					);
			// 查询
			countCust = tCustomerDao.selectCountForRiskCheck(custPramater);
			// 申请人配偶的手机号与其他申请人、其他申请人配偶的手机号相同；
			if (countCust > 0) {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_42);
			} else {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
			}
			l.add(mRezult);
		}
		// 按拦截，提醒，通过排序
		Collections.sort(l, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> arg0,
					Map<String, Object> arg1) {
				String strCheckrezult0 = Objects.firstNonNull(arg0.get(
						ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT), "").toString()
						;
				String strCheckrezult1 = Objects.firstNonNull(arg1.get(
						ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT), "").toString();
						;
				return strCheckrezult1.compareTo(strCheckrezult0);
			}
		});
		return l;
		}catch(Exception e){
			LOG.error("风险探测异常", e);
			return Lists.newArrayList();
		}
	}

	/**
	 * 风险预测规则3 是否重复申请-营业执照号检查
	 * 
	 */
	private void checkRegiCode(String loanId, String regiCode,
			String beginDateNow, Map<String, Object> mRezult) {
		Map<String, Object> mPramater = Maps.newHashMap();
		// 营业执照
		if (StringUtil.isEmpty(regiCode)) {
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
					ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
			mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
					ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_3_NULL);
		} else {
			mPramater.put(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_REGICODE,
					regiCode);
			mPramater.put(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_LOAN_ID,
					loanId);
			mPramater.put(ReviewNoteConstants.RISKDETECTIO_MAP_KEY_BEGINDATE,
					beginDateNow);
			List<TCreditApply> caList = dao.selectHistory(mPramater);
			int times = caList.size();
			if (times > 0) {
				String notice = ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_3;
				TCreditApply ca = caList.get(0);
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_1);
				String beginDate = DateUtil.getDateToString3(ca.getBeginDate());
				mRezult.put(
						ReviewNoteConstants.RISKDETECTIO_KEY_NOTICEINFOR,
						notice.replaceFirst(
								ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
								ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_3_3)
								.replaceFirst(
										ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
										String.valueOf(times + 1))
								.replaceFirst(
										ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
										beginDate)
								.replaceFirst(
										ReviewNoteConstants.RISKDETECTIO_VULUE_NOTICEINFOR_X,
										ca.getApplyStatusName()));
			} else {
				mRezult.put(ReviewNoteConstants.RISKDETECTIO_KEY_CHECKREZULT,
						ReviewNoteConstants.RISKDETECTIO_VULUE_CHECKREZULT_0);
			}
		}
	}

	/**
	 * @see com.hrbb.loan.pos.service.ReviewNoteService#selectCreditReportIdentity(java.lang.String)
	 */
	private TCreditReportIdentity selectCreditReportIdentity(String paperId,
			List<String> infoDetails) {
		Map<String, Object> reqMap = Maps.newHashMap();
		reqMap.put(ReviewNoteConstants.CREDIT_REPORT_MAP_KEY_CERTNO, paperId);
		reqMap.put(ReviewNoteConstants.CREDIT_REPORT_MAP_KEY_CERTTYPE,
				ReviewNoteConstants.CREDIT_REPORT_MAP_VALUE_CERTTYPE_0);
		TCreditReportIdentity cri = tCreditReportIdentityDao
				.selectInfor(reqMap);
		if (cri != null) {
			infoDetails.add(cri.getMobile());
			infoDetails.add(cri.getOfficeTel());
			infoDetails.add(cri.getHomeTel());
			infoDetails.add(cri.getPostAddr());
			infoDetails.add(cri.getRegiAddr());
		}
		return cri;
	}

	/**
	 * @see com.hrbb.loan.pos.service.ReviewNoteService#selectCreditReportIdentity(java.lang.String)
	 */
	private void selectCreditReportProfession(String queryId,
			List<String> infoDetails) {
		// 职业信息
		TCreditReportProfession crp = new TCreditReportProfession();
		// crp.setReportNo(reportNo);
		crp.setQueryId(queryId);
		List<TCreditReportProfession> crpList = tCreditReportProfessionDao
				.selectSelective(crp);
		for (int i = 0; i < crpList.size(); i++) {
			crp = crpList.get(i);
			infoDetails.add(crp.getEmployer());
			// 超过3条后结束，不做计算
			if (i > 2) {
				break;
			}
		}
	}

	/**
	 * POS流水准入判断
	 * 
	 * @param loanId
	 * @return
	 */
	private int checkPosSerialno(String loanId) {

		Map<String, Object> reqMap = Maps.newHashMap();
		reqMap.put("loanId", loanId);
		// 查询是否有pos流水
		long count = tPosSerialnoInfoDao.countMap(reqMap);
		if (count == 0) {
			return -1;
		}
		// 1.近6个月 有4个月POS流水记录
		// 4.近6个月POS累计交易金额超12万；
		// 日流水
		String lastDay = DateUtil.getLastMthNumStr(6);
		reqMap.put("tradeDate", lastDay);
		reqMap.put("posKind", ReviewNoteConstants.POSKIND_1);
		reqMap.put("tradeType", ReviewNoteConstants.TRADETYPE_CONSUMPTION);
		List<Map<String, Object>> resultDay6 = tPosSerialnoInfoDao
				.selectSumMapReview2(reqMap);

		int sum = 0;
		int num = 0;
		BigDecimal tradeAmtSum = BigDecimal.ZERO;

		for (Map<String, Object> map : resultDay6) {
			if (map.get("num") != null) {
				if(map.get("num") != null){
					num = Integer.parseInt(map.get("num").toString());
					
				}
				if (num > 0) {
					sum++;
					if (map.get("tradeAmtSum") != null) {
						tradeAmtSum = tradeAmtSum.add(new BigDecimal(map.get(
								"tradeAmtSum").toString()));
					}
				}
			}
		}
		// 月流水
		reqMap.put("tradeDate", lastDay.substring(0, 7));
		reqMap.put("posKind", ReviewNoteConstants.POSKIND_4);
		List<Map<String, Object>> resultMonth6 = tPosSerialnoInfoDao
				.selectSumMapReview2(reqMap);

		for (Map<String, Object> map : resultMonth6) {
			if (map.get("num") != null) {
				if(map.get("num") != null){
					num = Integer.parseInt(String.valueOf(map.get("num")));
					
				}
				if (num > 0) {
					sum++;
					if (map.get("tradeAmtSum") != null) {
						tradeAmtSum = tradeAmtSum.add(new BigDecimal(map.get(
								"tradeAmtSum").toString()));
					}
				}
			}
		}
		// 1.近6个月 没有4个月POS流水记录
		if (sum < 4) {
			return 1;
		}
		// 4.近6个月POS累计交易金额小于12万；
		if (tradeAmtSum.compareTo(new BigDecimal(120000)) < 0) {
			return 1;
		}

		sum = 0;
		// 2.近12个月有6个月POS流水记录；
		// 日流水
		lastDay = DateUtil.getLastMthNumStr(12);
		reqMap.put("tradeDate", lastDay);
		reqMap.put("posKind", ReviewNoteConstants.POSKIND_1);
		// 取得流水记录笔数
		sum = getSum(reqMap);
		// 月流水
		reqMap.put("tradeDate", lastDay.substring(0, 7));
		reqMap.put("posKind", ReviewNoteConstants.POSKIND_4);
		// 取得流水记录笔数
		sum = sum + getSum(reqMap);
		// 2.近12个月没有6个月POS流水记录；
		if (sum < 6) {
			return 1;
		}

		sum = 0;
		// 3.最近2个月必须有POS流水；
		// 日流水
		lastDay = DateUtil.getLastMthNumStr(2);
		reqMap.put("tradeDate", lastDay);
		reqMap.put("posKind", ReviewNoteConstants.POSKIND_1);
		// 取得流水记录笔数
		sum = getSum(reqMap);
		// 月流水
		reqMap.put("tradeDate", lastDay.substring(0, 7));
		reqMap.put("posKind", ReviewNoteConstants.POSKIND_4);
		// 取得流水记录笔数
		sum = sum + getSum(reqMap);
		// 3.最近2个月没有POS流水；
		if (sum == 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * 取得流水记录笔数
	 * 
	 * @param result
	 * @return
	 */
	private int getSum(Map<String, Object> reqMap) {
		List<Map<String, Object>> result = tPosSerialnoInfoDao
				.selectSumMapReview2(reqMap);
		int sum = 0;
		int num = 0;
		for (Map<String, Object> map : result) {
			if (map.get("num") != null) {
				num = Integer.parseInt(map.get("num").toString());
				if (num > 0) {
					sum++;
				}
			}
		}
		return sum;
	}
}
