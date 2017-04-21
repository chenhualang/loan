/**
 * 
 */
package com.hrbb.loan.spi;

import java.io.Serializable;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hrbb.loan.constants.CreditApplyConstants;
import com.hrbb.loan.pos.util.DateUtil;

/**
 * <p>Title: AbstractPOSService.java </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
 *     
 * @author linzhaolin@hrbb.com.cn
 * @version 
 * @date 2015年7月21日
 *
 * logs: 1. 
 */
public abstract class AbstractPOSService implements POSHService{

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	/*public   synchronized boolean addVectEle(String ele, Vector<String> vector){
		if(!vector.contains(ele)){
			vector.add(ele);
			return true;
		}else{
			return false;
		}
	}
	
	public   synchronized void removeEle(String ele, Vector<String> vector){
		if(vector.contains(ele)){
			vector.remove(ele);
		}
	}*/
	
	
	@Override
	public String getInChannelKind() {
		return POSHService.进件方式_PORTAL;
	}
	
	/**
	 * 贷款方式
	 * @return
	 */
	public String getLoanType(){
		return POSHService.贷款模式_自营业务;
	}
	
	/**
	 * 产品编号
	 * @return
	 */
	public String getProdId(){
		return CreditApplyConstants.posLoanFlag;
	}
	
	/**
	 * 产品名称
	 * @return
	 */
	public String getProdName(){
		return CreditApplyConstants.posLoanName;
	}
	
	/**
	 * 日期格式
	 * @return
	 */
	public String responseDateFormat(){
		return DateUtil.HRRB_SHORT_DATETIME_FORMAT_BRIEF;
	}
	
	/**
	 * 时间格式
	 * @return
	 */
	public String responseTimeFormat(){
		return DateUtil.HRRB_LONG_DATETIME_FORMAT_BRIEF;
	}
}
