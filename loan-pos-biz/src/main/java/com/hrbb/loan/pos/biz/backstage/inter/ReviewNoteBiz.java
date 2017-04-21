/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.biz.backstage.inter;

import java.util.List;

import com.hrbb.loan.pos.dao.entity.TReviewNote;

/**
 * 
 * @author marco
 * @version $Id: TReviewNoteBiz.java, v 0.1 2015-3-2 下午3:26:50 marco Exp $
 */
public interface ReviewNoteBiz {

	public TReviewNote getReviewNoteInfoById(TReviewNote tReviewNote);

	public List<TReviewNote> getReviewNoteInfoSelective(TReviewNote tReviewNote);

	public int insertTReviewNoteInfo(TReviewNote tReviewNote);

	public int deleteTReviewNoteInfo(TReviewNote tReviewNote);
	
	public String selectPeopleBankPhone(String paperId);
	
	public int updateByPrimaryKeySelective(TReviewNote record);
}