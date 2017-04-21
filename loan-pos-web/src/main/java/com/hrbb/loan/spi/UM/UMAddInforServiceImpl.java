/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.spi.UM;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hrbb.loan.pos.dao.TCreditApplyForReviewDao;
import com.hrbb.loan.pos.dao.entity.TCreditApply;
import com.hrbb.loan.pos.factory.SysConfigFactory;
import com.hrbb.loan.spiconstants.CreditApplyHServiceConstants;
import com.hrbb.loan.spiconstants.HServiceReturnCode;
import com.hrbb.sh.framework.HRequest;
import com.hrbb.sh.framework.HResponse;
import com.hrbb.sh.framework.HService;
import com.hrbb.sh.framework.HServiceException;
import com.hrbb.sh.framework.ftp.HttpClient;
import com.hrbb.sh.framework.ftp.ParamReqBean;
import com.hrbb.sh.framework.ftp.ParamResBean;

/**
 * 补充资料上传
 * 
 * @author marco
 * @version $Id: UMAddInforServiceImpl.java, v 0.1 2015-3-25 下午1:35:23 marco Exp
 *          $
 */
@Service("umAddInfo")
public class UMAddInforServiceImpl implements HService {

	private final Logger logger = LoggerFactory
			.getLogger(UMAddInforServiceImpl.class);

	@Autowired
	@Qualifier("tCreditApplyForReviewDao")
	private TCreditApplyForReviewDao daoCA;

//	@Value("#{systemInfo[url]}")
	private String URL;

	/**
	 * @see com.hrbb.sh.framework.HService#serve(com.hrbb.sh.framework.HRequest)
	 */
	@Override
	public HResponse serve(HRequest request) throws HServiceException {

		logger.debug("executing UMAddInforServiceImpl...");
		
		/*加载系统参数配置*/
		URL = SysConfigFactory.getInstance().getService("basicService").getPropertyValue("webUrl");

		Map<String, Object> props = request.getProperties();
		// 申请流水号
		String loanid = (String) props.get(CreditApplyHServiceConstants.loanid);
		// 取得申请信息
		TCreditApply ca = daoCA.selectOne(loanid);

		HResponse response = new HResponse();
		// 申请信息不存在
		if (ca == null) {
			response.setRespCode(HServiceReturnCode.LOANID_EXIST_ERROR
					.getReturnCode());
			response.setRespMessage(HServiceReturnCode.LOANID_EXIST_ERROR
					.getReturnMessage());
			response.setRespTime(new Date());
			logger.debug("UMAddInforServiceImpl end");
			return response;
		}
		// 影像资料文件包名
		String imagefilepackagename = (String) props
				.get(CreditApplyHServiceConstants.imagefilepackagename);
		logger.debug("loanid=" + loanid);
		logger.debug("imagefilepackagename=" + imagefilepackagename);
		// 其他字段校验

		// ftp
		ParamReqBean paramReqBean = new ParamReqBean();
		paramReqBean.setApplyNo(loanid);
		paramReqBean.setKeyValue(
				CreditApplyHServiceConstants.ftp_map_key_instno,
				CreditApplyHServiceConstants.ftp_instno_um);
		paramReqBean.setCustId(ca.getCustId());
		logger.debug("CustId=" + ca.getCustId());
		paramReqBean.setVersion(CreditApplyHServiceConstants.ftp_version);
		paramReqBean.setBizType(CreditApplyHServiceConstants.ftp_biztype);
		paramReqBean.setTransType(CreditApplyHServiceConstants.ftp_transtype);
		paramReqBean.setKeyValue(
				CreditApplyHServiceConstants.ftp_map_key_instno,
				CreditApplyHServiceConstants.ftp_instno_um);
		paramReqBean.setKeyValue(
				CreditApplyHServiceConstants.ftp_map_key_prefix,
				CreditApplyHServiceConstants.ftp_prefix_app);
		paramReqBean.setKeyValue(
				CreditApplyHServiceConstants.ftp_map_key_remotefilename,
				imagefilepackagename);
		paramReqBean.setKeyValue(
				CreditApplyHServiceConstants.ftp_map_key_LocalSubFolderNameDef,
				loanid);

		paramReqBean.setKeyValue(CreditApplyHServiceConstants.ftp_map_key_url,
				URL + CreditApplyHServiceConstants.ftp_map_key_returnUrl);
		logger.debug("url="
				+ paramReqBean
						.getValueByKey(CreditApplyHServiceConstants.ftp_map_key_url));
		ParamResBean paramResBean = HttpClient.send(paramReqBean);
		String respCode = null;
		String respMsg = null;
		if (paramResBean == null) {
			respCode = HServiceReturnCode.FTP_ERROR.getReturnCode();
			respMsg = HServiceReturnCode.FTP_ERROR.getReturnMessage();
		} else {
			respCode = paramResBean.getRespCode();
			respMsg = paramResBean.getRespMsg();
		}
		logger.debug("ftp respCode=" + respCode);
		logger.debug("ftp respMsg=" + respMsg);
		// 异常的时候
		// if (paramResBean == null
		// || !CreditApplyHServiceConstants.ftp_resp_code_000
		// .equals(respCode)) {
		// return null;
		// }
		response.setRespCode(HServiceReturnCode.SUCCESS.getReturnCode());
		response.setRespMessage(HServiceReturnCode.SUCCESS.getReturnMessage());
		response.setRespTime(new Date());
		logger.debug("UMAddInforServiceImpl end");
		return response;
	}
}
