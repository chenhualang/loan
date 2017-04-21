/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.biz.backstage.inter.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hrbb.loan.pos.biz.backstage.inter.ReviewNoteBiz;
import com.hrbb.loan.pos.dao.entity.TReviewNote;
import com.hrbb.loan.pos.service.ReviewNoteService;

/**
 * 
 * @author marco
 * @version $Id: TReviewNoteBiz.java, v 0.1 2015-3-2 下午3:26:50 marco Exp $
 */
@Component("reviewNoteBiz")
public class ReviewNoteBizImpl implements ReviewNoteBiz {

	private Logger LOG = LoggerFactory.getLogger(ReviewNoteBizImpl.class);

	@Autowired
	@Qualifier("reviewNoteService")
	private ReviewNoteService reviewNoteService;

	public TReviewNote getReviewNoteInfoById(TReviewNote tReviewNote) {
		return reviewNoteService.getReviewNoteInfoById(tReviewNote);
	}

	public List<TReviewNote> getReviewNoteInfoSelective(TReviewNote tReviewNote) {
		return reviewNoteService.getReviewNoteInfoSelective(tReviewNote);
	}

	public int insertTReviewNoteInfo(TReviewNote tReviewNote) {
		return reviewNoteService.insertTReviewNoteInfo(tReviewNote);
	}

	public int deleteTReviewNoteInfo(TReviewNote tReviewNote) {
		return reviewNoteService.deleteTReviewNoteInfo(tReviewNote);
	}

	public String selectPeopleBankPhone(String paperId) {
		return reviewNoteService.selectPeopleBankPhone(paperId);
	}

	/** 
	 * @see com.hrbb.loan.pos.biz.backstage.inter.ReviewNoteBiz#updateByPrimaryKeySelective(com.hrbb.loan.pos.dao.entity.TReviewNote)
	 */
	@Override
	public int updateByPrimaryKeySelective(TReviewNote record) {
		return reviewNoteService.updateByPrimaryKeySelective(record);
	}
}
