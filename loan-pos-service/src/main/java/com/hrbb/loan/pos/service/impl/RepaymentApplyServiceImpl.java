package com.hrbb.loan.pos.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.hrbb.loan.pos.dao.TCfgChannelAccountDao;
import com.hrbb.loan.pos.dao.TContractManagementDao;
import com.hrbb.loan.pos.dao.TPaymentApplyDao;
import com.hrbb.loan.pos.dao.TReceiptInfoDao;
import com.hrbb.loan.pos.dao.TRepaymentApplyDao;
import com.hrbb.loan.pos.dao.entity.TCfgChannelAccount;
import com.hrbb.loan.pos.dao.entity.TContractManagement;
import com.hrbb.loan.pos.dao.entity.TPaybackApplyInfo;
import com.hrbb.loan.pos.dao.entity.TPaymentApply;
import com.hrbb.loan.pos.dao.entity.TReceiptInfo;
import com.hrbb.loan.pos.dao.entity.TRepaymentPlan;
import com.hrbb.loan.pos.service.RepaymentApplyService;
import com.hrbb.loan.pos.service.TRepaymentPlanService;
import com.hrbb.loan.pos.util.DateUtil;
import com.hrbb.loan.pos.util.IdUtil;
import com.hrbb.loan.pos.util.StringUtil;

@Service("repaymentApplyService")
public class RepaymentApplyServiceImpl implements RepaymentApplyService {
    Logger                                    logger                = LoggerFactory
                                                                        .getLogger(RepaymentApplyServiceImpl.class);
    @Autowired
    private TRepaymentApplyDao                tRepaymentApplyDao;

    @Autowired
    private TPaymentApplyDao                  tPaymentApplyDao;

    @Autowired
    private TReceiptInfoDao                   tReceiptInfoDao;

    @Autowired
    private TContractManagementDao            contDao;

    @Autowired
    private TCfgChannelAccountDao             tCfgChannelAccountDao;

    @Autowired
    private TRepaymentPlanService             repaymentPlanService;

    //自助渠道可以七天内还款
    private static final ImmutableSet<String> canRepyInSevenDaysSet = new ImmutableSet.Builder<String>()
                                                                        .add("ZZ").build();

    private static final boolean canRepayInSevenDays(String channel) {
        if (canRepyInSevenDaysSet.contains(channel)) {
            return true;
        } else {
            return false;
        }
    }

    private static final String TYPE_AHEAD   = "01";

    private static final String TYPE_OVERDUE = "02";

    private static final String STATUS_INIT  = "10";

    private TPaymentApply queryPaymentApplyNo(String paymentApplyNo) throws Exception {
        try {
            TPaymentApply paymentApply = tPaymentApplyDao.selectByPrimaryKey(paymentApplyNo);

            if (paymentApply == null) {
                throw new Exception(String.format("no listid %s existed", paymentApplyNo));
            }

            return paymentApply;
        } catch (Exception ex) {
            throw new Exception(String.format("no listid %s existed", paymentApplyNo), ex);
        }
    }

    /** 
     * @see com.hrbb.loan.pos.service.RepaymentApplyService#applyRepayment(java.util.Map)
     */
    @Override
    public String applyRepayment(Map<String, Object> request) throws Exception {
        Assert.notNull(request, "doesn't allow empty request");
        String paymentApplyNo = (String) request.get("listid");
        String applyNo = "";
        TPaymentApply paymentApply = null;
        if (!StringUtils.isEmpty(paymentApplyNo)) {
            paymentApply = queryPaymentApplyNo(paymentApplyNo);
        } else {
            throw new Exception("借据号不能为空!");
        }

        /**
         * new repayment validated
         */
        String type = (String) request.get("retutype");
        String preType = (String) request.get("aheakind");
        String accNo = (String) request.get("subsaccno");
        String applyDateReq = (String) request.get("retudate");
        String merchantNo = (String) request.get("stdmerno");
        String contNo = (String) paymentApply.getContNo();
        String custId = (String) paymentApply.getCustId();

        Date applyDate = null;
        try {
            applyDate = DateUtil.getStrToDate1(applyDateReq);
        } catch (Exception ex) {
            throw new Exception(String.format("invalid date format in field retudate : %s",
                applyDateReq), ex);
        }

        if (!(!StringUtils.isEmpty(type) && (TYPE_AHEAD.equalsIgnoreCase(type) || TYPE_OVERDUE
            .equalsIgnoreCase(type)))) {
            throw new Exception(String.format("invalid retutype format in field retudate : %s",
                type));
        }

        String repaymentNo = IdUtil.getRepaymentApplyId();
        if (StringUtils.isEmpty(repaymentNo)) {
            throw new Exception("failed to generate repaymentNo");
        }
        TPaybackApplyInfo tPaybackApplyInfo = new TPaybackApplyInfo();
        tPaybackApplyInfo.setPaybackApplyId(repaymentNo);
        tPaybackApplyInfo.setPaybackStatus("00");
        tPaybackApplyInfo.setType(type);
        tPaybackApplyInfo.setAdvancedPaybackType(preType);
        tPaybackApplyInfo.setAccNo(accNo);
        tPaybackApplyInfo.setPaybackApplyDate(applyDate);
        tPaybackApplyInfo.setMerchantNo(merchantNo);
        tPaybackApplyInfo.setPaybackRunningRecordId(applyNo);
        tPaybackApplyInfo.setContNo(contNo);
        tPaybackApplyInfo.setCustId(custId);
        tPaybackApplyInfo.setPaybackAmount(request.get("capital") == null ? new BigDecimal(0)
            : new BigDecimal((String) request.get("capital")));
        tPaybackApplyInfo.setPaybackTotalAmount(request.get("sumamt") == null ? new BigDecimal(0)
            : new BigDecimal((String) request.get("sumamt")));
        tPaybackApplyInfo.setPaybackInterest(request.get("interest") == null ? new BigDecimal(0)
            : new BigDecimal((String) request.get("interest")));
        String receiptId = tReceiptInfoDao.getReceiptIdByPayApplyId(paymentApplyNo);
        String term = getTerm(receiptId);
        tPaybackApplyInfo.setTerm(term);
        TReceiptInfo receipt = tReceiptInfoDao.selectByPrimaryKey(receiptId);
        TContractManagement contract = contDao.getContractInfoByContNo(contNo);
        Date beginDate = receipt.getBeginDate();
        long days  = (applyDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);
        if (days < 7 && !canRepayInSevenDays(contract.getChannel())) {
            throw new Exception("不能在7天内还款!");
        }
        //还款总额不能大于借据金额
        //先查询还款的总金额(只包括申请中的，待扣款的和扣款成功的) + 加上本次还款的
        Map<String, Object> reqMap = Maps.newHashMap();
        reqMap.put("receiptId", receiptId);
        BigDecimal totalPaybackAmt = new BigDecimal(
            tPaymentApplyDao.sumHasRepayedAmt(reqMap) == null ? "0"
                : tPaymentApplyDao.sumHasRepayedAmt(reqMap)).add(tPaybackApplyInfo
            .getPaybackAmount());
        logger.info("总还款金额为:" + totalPaybackAmt);

        //查询出借据金额
        logger.info("借据金额为:" + receipt.getLoanTotalBalance());
        //借据金额-总还款金额<=0时，返回错误，还款总金额大于用款金额
        if (receipt.getLoanTotalBalance().compareTo(totalPaybackAmt) < 0) {
            throw new Exception("over repayment");
        }
        if (StringUtil.isEmpty(receiptId)) {
            throw new Exception("failed to get receiptId !");
        }
        tPaybackApplyInfo.setReceiptId(receiptId);

        logger.info("还款申请信息=[{}]" , tPaybackApplyInfo);
        try {
            tRepaymentApplyDao.insert(tPaybackApplyInfo);
        } catch (Exception ex) {
            throw new Exception("failed to insert repayment record...", ex);
        }

        return repaymentNo;
    }

    /**
     * 获取最近要还的期次
     * 
     * @param receiptId
     * @return
     */
    private String getTerm(String receiptId) {
        TRepaymentPlan repaymentPlan = repaymentPlanService.getRecentlyPlan(receiptId);
        String term = repaymentPlan.getPeriod(); //还款期次
        return term;
    }

    @Override
    public List<Map<String, Object>> queryRepaymentListByZzApp(Map<String, Object> map) {
        return tRepaymentApplyDao.selectListByZzApp(map);
    }

    @Override
    public String paybackApply(Map<String, Object> request) throws Exception {
        Assert.notNull(request, "doesn't allow empty request");
        String paymentApplyNo = (String) request.get("issueid");

        /*查询用款申请*/
        if (StringUtils.isEmpty(paymentApplyNo)) {
            throw new Exception("doesn't allow empty listid");
        }

        TPaymentApply paymentApply = queryPaymentApplyNo(paymentApplyNo);
        /* 参数校验 */
        String amount = (String) request.get("sumamt");
        String preType = (String) request.get("aheakind");
        String accNo = (String) request.get("subsaccno");
        String applyDateReq = (String) request.get("retudate");
        Date applyDate = null;
        try {
            applyDate = DateUtil.getStrToDate1(applyDateReq);
        } catch (Exception ex) {
            throw new Exception(String.format("invalid date format in field retudate : %s",
                applyDateReq), ex);
        }
        String stdrpno = (String) request.get("stdrpno");

        BigDecimal applyAmt = null;

        if (!StringUtils.isEmpty(amount)) {
            try {
                applyAmt = BigDecimal.valueOf(Double.valueOf(amount));
            } catch (Exception ex) {
                throw new Exception("invalid sumamt format");
            }
        } else {
            throw new Exception("doesn't allow empty sumamt");
        }

        /*生成还款申请*/
        String repaymentNo = IdUtil.getRepaymentApplyId();
        if (StringUtils.isEmpty(repaymentNo)) {
            throw new Exception("failed to generate repaymentNo");
        }

        TPaybackApplyInfo tPaybackApplyInfo = new TPaybackApplyInfo();
        tPaybackApplyInfo.setPaybackApplyId(repaymentNo);
        tPaybackApplyInfo.setPaybackStatus(STATUS_INIT);
        tPaybackApplyInfo.setAdvancedPaybackType(preType);
        tPaybackApplyInfo.setPaybackAmount(applyAmt);
        tPaybackApplyInfo.setAccNo(accNo);
        tPaybackApplyInfo.setPaybackApplyDate(applyDate);
        //tPaybackApplyInfo.setPaybackRunningRecordId(applyNo);
        tPaybackApplyInfo.setContNo((String) paymentApply.getContNo());
        tPaybackApplyInfo.setCustId((String) paymentApply.getCustId());
        String receiptId = tReceiptInfoDao.getReceiptIdByPayApplyId(paymentApplyNo);
        if (StringUtil.isEmpty(receiptId)) {
            throw new Exception("failed to get receiptId !");
        }
        TReceiptInfo receipt = tReceiptInfoDao.selectByPrimaryKey(receiptId);
        Date beginDate = receipt.getBeginDate();
        long days = (applyDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);
        if (days < 7) {
            throw new Exception("It is not allowed to payback the money in less than 7 days!");
        }
        tPaybackApplyInfo.setReceiptId(receiptId);
        logger.info("还款申请信息为" + tPaybackApplyInfo);
        try {
            tRepaymentApplyDao.insert(tPaybackApplyInfo);
        } catch (Exception ex) {
            throw new Exception("failed to insert repayment record...", ex);
        }
        return repaymentNo;
    }

    @Override
    public List<TCfgChannelAccount> getEffectAccount() {
        return tCfgChannelAccountDao.getEffectAccount();
    }

    @Override
    public TCfgChannelAccount getChannelAccount(String channel) {
        return tCfgChannelAccountDao.getChannelAccount(channel);
    }
}
