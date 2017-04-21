package com.hrbb.loan.pos.service;

import java.util.List;
import java.util.Map;

/**
 *<h1></h1>
 *@author Johnson Song
 *@version Id: LoanPosBusinessCodeService.java, v 1.0 2015-3-9 下午4:29:00 Johnson Song Exp
 */
public interface LoanPosBusinessCodeService {
	
	public List<Map<String, Object>> getItemNames(String codeNo);
	
	public String getItemNameByNo(String codeNo, String itemNo);
	
	public List<Map<String, Object>> getProvinceCityOrDic(String itemNoLike);
	
	public List<Map<String, Object>> getSeletiveMap(Map<String, Object> reqMap);
	
	/**
	 * 通过displayNo控制展示顺序
	 * @param codeNo
	 * @return
	 */
	public List<Map<String, Object>> getItemNamesWithExtOrder(Map<String, Object> reqMap);
	
	/**
	 * 根据codeNo和itemNo获取ExtAttr
	 * @param codeNo
	 * @param itemNo
	 * @return
	 */
	public String getExtAttrByNo(String codeNo, String itemNo);

    /**
     * 根据itermName查询itemNo
     * 
     * @param codeNo
     * @param itemName
     * @return
     */
    String getItemNoByName(String codeNo, String itemName);
    
	/**
	 * 获取以逗号分隔的多个代码名称
	 * @param codeNo
	 * @param itemNos 多个代码已逗号分隔
	 * @return
	 */
	public String getSplitedItemName(String codeNo, String itemNos);
	
	/**
	 * 
	 * @param codeNo
	 * @param itemNos
	 * @param splitor
	 * @return
	 */
	public String getSplitedItemName(String codeNo, String itemNos, String splitor);
}
