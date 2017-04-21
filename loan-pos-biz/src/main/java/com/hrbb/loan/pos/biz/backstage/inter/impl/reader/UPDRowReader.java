/**
 * 
 */
package com.hrbb.loan.pos.biz.backstage.inter.impl.reader;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.hrbb.loan.pos.util.DateUtil;
import com.hrbb.loan.pos.util.constants.BusinessDictionaryConstants;

/**
 * <p>Title: UPDRowReader.java </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
 *     
 * @author linzhaolin@hrbb.com.cn
 * @version 
 * @date 2015年4月28日
 *
 * logs: 1. 
 */
public class UPDRowReader extends POSRowReader {
	
	public UPDRowReader(String loanId, String posType){
		/*
		0.商户名称
		1.商户号
		2.MCC
		3.交易时间	20140709141241
		4.交易金额
		5.交易卡号
		6.交易卡种
		7.交易类型
		 */
		RowMeta[] rm = {new RowMeta(3,DateUtil.HRRB_LONG_DATETIME_FORMAT_BRIEF,DateUtil.HRRB_LONG_DATETIME_FORMAT)};		//yyyyMMddHHmmss -> yyyy-MM-dd HH:mm:ss
		super.addMeta(rm);
		
		this.posChannel = BusinessDictionaryConstants.BIZ_CHANNEL_KQ;
		this.loanId = loanId;
		this.posType = posType;
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.biz.backstage.inter.impl.reader.POSRowReader#processRow(java.util.List)
	 */
	@Override
	protected Map<String, Object> processRow(List<String> row) {
		
		Map<String, Object> insertMap = Maps.newHashMap();
		insertMap.put(RowMeta.LOAN_ID, loanId);
		insertMap.put(RowMeta.POS_CHANNEL, posChannel);
		insertMap.put(RowMeta.POS_KIND, posType);
		insertMap.put(RowMeta.MERCHANT_NAME, row.get(0));
		insertMap.put(RowMeta.MERCHANT_ID, row.get(1));
		insertMap.put(RowMeta.MERCHANT_TYPE_CODE, row.get(2));
		String dateStr = "";
		String timeStr = "";
		String dateTime = row.get(3);
		if(dateTime!=null && dateTime.length()>=19){
			dateStr = dateTime.substring(0,10);
			timeStr = dateTime.substring(11,dateTime.length());
			//----正常时间交易
			String sHour = timeStr.substring(0,2);
			int iHour = Integer.valueOf(sHour);
			if(iHour>=7 && iHour <=23){
				insertMap.put(RowMeta.REG_TIME_TRADE_AMT, row.get(4));		//7~23正常营业时间
			}
		}else{
			dateStr = dateTime;
		}
		insertMap.put(RowMeta.TRADE_DATE, dateStr);
		insertMap.put(RowMeta.TRADE_TIME, timeStr);
		insertMap.put(RowMeta.TRADE_AMT, row.get(4));
		String cardNo = row.get(5);
		insertMap.put(RowMeta.TRADE_CARD_NO, cardNo);
		insertMap.put(RowMeta.TRADE_CARD_KIND, row.get(6));
		insertMap.put(RowMeta.TRADE_TYPE, row.get(7));
		if(cardNo!=null && cardNo.trim().length()>4){
			insertMap.put(RowMeta.TRADE_LAST_FOUR_STATE, cardNo.substring(cardNo.length()-4, cardNo.length()));
		}
		insertMap.put(RowMeta.TRADE_NUM, "1");		//明细笔数为1
		
		return insertMap;
	}

}
