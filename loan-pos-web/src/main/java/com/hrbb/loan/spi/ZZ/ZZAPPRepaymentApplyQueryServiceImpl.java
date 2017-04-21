package com.hrbb.loan.spi.ZZ;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hrbb.loan.pos.biz.backstage.inter.LoanPosPaybackApplyBiz;
import com.hrbb.loan.pos.util.StringUtil;
import com.hrbb.loan.pos.util.ValidateUtil;
import com.hrbb.loan.spiconstants.CreditApplyHServiceConstants;
import com.hrbb.loan.spiconstants.HServiceReturnCode;
import com.hrbb.loan.spiconstants.PaymentApplyHServiceConstants;
import com.hrbb.sh.framework.HResponse;
import com.hrbb.sh.framework.HServiceException;

/**
 * TransType:AP0012 -> 用款申请信息查询.
 * 
 * @author xiongshaogang
 * @version $Id: ZZAPPPaymentApplyQueryServiceImpl.java, v 0.1 2015年4月23日 下午8:01:45 xiongshaogang Exp $
 */
@Service("zzAppRepaymentApplyQuery")
public class ZZAPPRepaymentApplyQueryServiceImpl extends ZZAPPFoundationServiceImpl {

    Logger logger = LoggerFactory.getLogger(ZZAPPRepaymentApplyQueryServiceImpl.class);
    
    @Autowired
    private LoanPosPaybackApplyBiz loanPosPaybackApplyBiz;
    
    /**
     * 
     * @see com.hrbb.sh.framework.HService#serve(com.hrbb.sh.framework.HRequest)
     */
	@Override
	protected HResponse doProcesser(HResponse resp, Map<String, String> headerMap,
                                    Map<String, String> bodyMap) throws HServiceException {
	    
	    logger.debug("in ZZAPPRepaymentApplyQueryServiceImpl...");
	    
        // 1. 组装检索要素
        Map<String, Object> queryMap = Maps.newHashMap();
        if (StringUtil.isNotEmpty(bodyMap.get(PaymentApplyHServiceConstants.loanid))) {
            queryMap.put("loanId", bodyMap.get(PaymentApplyHServiceConstants.loanid));
        }
        if (StringUtil.isNotEmpty(bodyMap.get(PaymentApplyHServiceConstants.custid))) {
            queryMap.put("custId", bodyMap.get(PaymentApplyHServiceConstants.custid));
        }
        if (StringUtil.isNotEmpty(bodyMap.get(PaymentApplyHServiceConstants.custname))) {
            queryMap.put("custName", bodyMap.get(PaymentApplyHServiceConstants.custname));
        }
        if (StringUtil.isNotEmpty(bodyMap.get(PaymentApplyHServiceConstants.paperkind))) {
            queryMap.put("paperKind", bodyMap.get(PaymentApplyHServiceConstants.paperkind));
        }
        if (StringUtil.isNotEmpty(bodyMap.get(PaymentApplyHServiceConstants.paperid))) {
            queryMap.put("paperId", bodyMap.get(PaymentApplyHServiceConstants.paperid));
        }
        if (StringUtil.isNotEmpty(bodyMap.get("contno"))) {
            queryMap.put("contNo", bodyMap.get("contno"));
        }
        if (StringUtil.isNotEmpty(bodyMap.get("cncontno"))) {
            queryMap.put("cnContNo", bodyMap.get("cncontno"));
        }
        if (StringUtil.isNotEmpty(bodyMap.get("payapplyid"))) {
            queryMap.put("payApplyId", bodyMap.get("payapplyid"));
        }
        if (StringUtil.isNotEmpty(bodyMap.get(CreditApplyHServiceConstants.begindate))) {
            queryMap.put("beginDate", bodyMap.get(CreditApplyHServiceConstants.begindate));
        }
        if (StringUtil.isNotEmpty(bodyMap.get(CreditApplyHServiceConstants.enddate))) {
            queryMap.put("endDate", bodyMap.get(CreditApplyHServiceConstants.enddate));
        }
        
        // 2. 用款明细查询
        List<Map<String, Object>> retList = loanPosPaybackApplyBiz.queryPaybackDetailsByMaps(queryMap);
        if (retList == null || retList.size() < 1) {
            bodyMap.put("respcode", HServiceReturnCode.QUERY_NULL_ERROR.getReturnCode());
            bodyMap.put("respmsg", HServiceReturnCode.QUERY_NULL_ERROR.getReturnMessage());
            resp.setRespCode(HServiceReturnCode.QUERY_NULL_ERROR.getReturnCode());
            resp.setRespMessage(HServiceReturnCode.QUERY_NULL_ERROR.getReturnMessage());
            resp.setRespTime(new Date());
            return getBlankHResponse(resp, headerMap, bodyMap);
        }
        
        // 3. 格式化
        setFormat(retList);
        
        // 4. 成功应答
        Map<String, Object> rootMap = Maps.newHashMap();
        Map<String, Object> detailsMap = Maps.newHashMap();
        detailsMap.put("details", retList);
        detailsMap.put("respcode", HServiceReturnCode.SUCCESS.getReturnCode());
        detailsMap.put("respmsg", HServiceReturnCode.SUCCESS.getReturnMessage());
        
        // 4.1 签名处理
        headerMap.put("Mac", sign(detailsMap, null));
        
        rootMap.put("HrbbHeader", headerMap);
        rootMap.put("HrbbBody", detailsMap);
        resp.setRespCode(HServiceReturnCode.SUCCESS.getReturnCode());
        resp.setRespMessage(HServiceReturnCode.SUCCESS.getReturnMessage());
        resp.setRespTime(new Date());
        resp.setProperties(rootMap);
        logger.info("回写数据: " + resp.toString());
		logger.debug("out ZZAPPRepaymentApplyQueryServiceImpl...");
		return resp;
	}
	
	/**
     * format.
     * 
     * @param formatList
     */
    private void setFormat(List<Map<String, Object>> formatList) {
        if (formatList.isEmpty()) {
            return;
        }
        
        Map<String, Object> tmpMap = null;
        for (int i = 0; i < formatList.size(); i++) {
            tmpMap = formatList.get(i);
            if (tmpMap.isEmpty()) {
                continue;
            }
            
            for (Map.Entry<String, Object> en : tmpMap.entrySet()) {
                String key = en.getKey();
                
                TmpMsgBodyParams.add(key);
                
                // format process
                if (tmpMap.get(key) == null) {
                    tmpMap.put(key, "");
                    continue;
                }
                
                // decimal
                if ("sumamt".equals(key)
                        || "rcapi".equals(key)
                        || "rinte".equals(key)) {
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(5);
                    nf.setRoundingMode(RoundingMode.CEILING);
                    nf.setGroupingUsed(false);
                    tmpMap.put(key, nf.format(tmpMap.get(key)));
                }
                
                if ("status".equals(key)) {
                    tmpMap.put(key, zzAppRepaymentStatusMap.get(tmpMap.get(key)));
                }
            }
            
            // 无数据字段赋空
            for (String param : MsgBodyParams) {
//                logger.info("设置应答字段 [" + param + "]");
                if (!TmpMsgBodyParams.contains(param)) {
                    tmpMap.put(param, "");
//                    logger.info("无数据字段赋空 [" + param + "]");
                }
            }
        }
    }
    
    /**
     * 不等贷 还款申请状态转义.
     * 
     * 待发送指令    00      还款中 10
              等待扣款    10      还款中 10
              全额扣款    20      还款成功    20
              部分扣款    21      还款失败    30
              扣款失败    90      还款失败    30
              主动撤销    30      主动撤销    90
     */
    private static ImmutableMap<String, String> zzAppRepaymentStatusMap = new ImmutableMap.Builder<String, String>().put("10", "10")
                                                                                                           .put("00", "10")
                                                                                                           .put("20", "20")
                                                                                                           .put("21", "30")
                                                                                                           .put("90", "30")
                                                                                                           .put("30", "90")
                                                                                                           .build();
    
    private static List<String> MsgBodyParams = new ArrayList<String>();
    static {
        MsgBodyParams.add("custid");
        MsgBodyParams.add("custname");
        MsgBodyParams.add("paperkind");
        MsgBodyParams.add("paperid");
        MsgBodyParams.add("loanid");
        MsgBodyParams.add("contno");
        MsgBodyParams.add("cncontno");
        MsgBodyParams.add("payapplyid");
        MsgBodyParams.add("retulistid");
        MsgBodyParams.add("retutype");
        MsgBodyParams.add("subsaccno");
        MsgBodyParams.add("acdate");
        MsgBodyParams.add("sumamt");
        MsgBodyParams.add("rcapi");
        MsgBodyParams.add("rinte");
        MsgBodyParams.add("rfine");
        MsgBodyParams.add("status");
        MsgBodyParams.add("paybackamt");
        MsgBodyParams.add("paybackdate");
    }
    private List<String> TmpMsgBodyParams = Lists.newArrayList();

    /**
     * 请求包校验处理.
     * 
     * @param headerMap
     * @param bodyMap
     * @return
     * @throws Exception 
     */
    protected boolean validate(Map<String, String> headerMap, Map<String, String> bodyMap,
                             HResponse resp) throws HServiceException {
        if (!validateHeader(headerMap, bodyMap, resp)) {
            return false;
        }

        // 联合校验
        boolean idx = true;
        try {
            if (StringUtil.isEmpty(bodyMap.get(CreditApplyHServiceConstants.loanid))
                && StringUtil.isEmpty(bodyMap.get(CreditApplyHServiceConstants.custid))
                && StringUtil.isEmpty(bodyMap.get(CreditApplyHServiceConstants.custname))
                && StringUtil.isEmpty(bodyMap.get(CreditApplyHServiceConstants.paperid))
                && StringUtil.isEmpty(bodyMap.get(CreditApplyHServiceConstants.paperkind))
                && StringUtil.isEmpty(bodyMap.get("contno"))
                && StringUtil.isEmpty(bodyMap.get("cncontno"))
                && StringUtil.isEmpty(bodyMap.get("payapplyid"))) {
                logger.error("检索条件不完整 :[]", "");
                bodyMap.put("respcode", HServiceReturnCode.QUERY_KEY_ERROR.getReturnCode());
                bodyMap.put("respmsg", HServiceReturnCode.QUERY_KEY_ERROR.getReturnMessage());
                resp.setRespCode(HServiceReturnCode.QUERY_KEY_ERROR.getReturnCode());
                resp.setRespMessage(HServiceReturnCode.QUERY_KEY_ERROR.getReturnMessage());
                idx = false;
            } else if (!StringUtil.isEmpty(bodyMap.get(CreditApplyHServiceConstants.paperid))
                    && !ValidateUtil.checkIdNumber2(bodyMap.get(CreditApplyHServiceConstants.paperid))) {
                logger.error("证件代码不合法:[]",
                    bodyMap.get(CreditApplyHServiceConstants.paperkind) + "_"
                            + CreditApplyHServiceConstants.paperid);
                bodyMap.put("respcode", HServiceReturnCode.QUERY_KEY_ERROR.getReturnCode());
                bodyMap.put("respmsg", HServiceReturnCode.QUERY_KEY_ERROR.getReturnMessage());
                resp.setRespCode(HServiceReturnCode.QUERY_KEY_ERROR.getReturnCode());
                resp.setRespMessage(HServiceReturnCode.QUERY_KEY_ERROR.getReturnMessage());
                idx = false;
            }

        } catch (Exception e) {
            logger.error("校验 异常:[]", e.getMessage());
            return false;
        }

        return idx;
    }

    /**
     * 失败应答.
     * 
     * @param response
     * @return
     */
    protected HResponse getBlankHResponse(HResponse response, Map<String, String> headerMap, Map<String, String> bodyMap) {
        Map<String, Object> respMap = Maps.newHashMap();
        Map<String, Object> tmpBodyMap = Maps.newHashMap();
        List<Object> respList = Lists.newArrayList();
        tmpBodyMap.put("details", respList);
        tmpBodyMap.put("respcode", bodyMap.get("respcode"));
        tmpBodyMap.put("respmsg", bodyMap.get("respmsg"));
        
        // 5.1 签名处理
        headerMap.put("Mac", sign(tmpBodyMap, null));
        
        respMap.put("HrbbHeader", headerMap);
        respMap.put("HrbbBody", tmpBodyMap);
        response.setProperties(respMap);
        return response;
    }
}
