/**
 * 
 */
package com.hrbb.loan.pos.biz.bean;

import com.hrbb.sh.framework.HServiceException;
import com.hrbb.sh.framework.ftp.HttpClient;
import com.hrbb.sh.framework.ftp.ParamReqBean;
import com.hrbb.sh.framework.ftp.ParamResBean;

/**
 * <p>Title: Notify4Archive.java </p>
 * <p>Description: 通知文档服务器进行归档处理 </p>
 * <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
 *     
 * @author linzhaolin@hrbb.com.cn
 * @version 
 * @date 2015年7月27日
 *
 * logs: 1. 
 */
public class Notify4Archive extends AbsInternalService {
	
	private String loanId;
	
	private String imagefilepackagename;
	
	private String url;
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public void setImagefilepackagename(String imagefilepackagename) {
		this.imagefilepackagename = imagefilepackagename;
	}

	@Override
	public boolean initService() throws HServiceException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean processService() throws HServiceException {
		logger.debug("通知ftp做文件上传处理...");
		
		logger.debug("loanid=" + loanId);
		logger.debug("imagefilepackagename=" + imagefilepackagename);
		// ftp请求参数
		ParamReqBean paramReqBean = new ParamReqBean();
		paramReqBean.setApplyNo(loanId);
		//机构代码
		paramReqBean.setKeyValue("InstNo","LC");
		//消息版本
		paramReqBean.setVersion("1.0.0");
		//业务类型
		paramReqBean.setBizType("0001");
		//交易类型
		paramReqBean.setTransType("0001");
		//阶段前缀
		paramReqBean.setKeyValue("Prefix","APP");
		//远程文件名
		paramReqBean.setKeyValue("RemoteFileName", imagefilepackagename);
		//流水号
		paramReqBean.setKeyValue("LocalSubFolderNameDef", loanId);
		paramReqBean.setKeyValue("url", url + "creditApply/uploadImageDataBackController.do");
		logger.debug("--URL--:" + url);
		logger.debug("url="+ paramReqBean.getValueByKey("url"));
		ParamResBean paramResBean = HttpClient.send(paramReqBean);
		if (paramResBean == null) {
			rspResult.put("respCode", "999");
			rspResult.put("respMsg", "HttpClient error!");
			return false;
		} else {
			String respCode = paramResBean.getRespCode();
			rspResult.put("respCode", paramResBean.getRespCode());
			rspResult.put("respMsg", paramResBean.getRespMsg());
			if(respCode!=null && respCode.equals("000")){
				return true;
			}else{
				return false;
			}
		}
	}

	@Override
	public boolean processService(String fileName, byte[] bytes)
			throws HServiceException {
		// TODO Auto-generated method stub
		return false;
	}

}
