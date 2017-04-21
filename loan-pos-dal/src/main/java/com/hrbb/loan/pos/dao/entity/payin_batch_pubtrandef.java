package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class payin_batch_pubtrandef implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -435367634840141056L;

	private String batchno;

    private Integer seqno;

    private String trandate;

    private String sysdate;

    private Integer serseqno;

    private String dbrc;

    private String dbaccount;

    private String cbrc;

    private String craccount;

    private BigDecimal amt;

    private String keyword;

    private String channelid;

    private String statcode;

    private String clsprdcode;

    private String memocode;

    private String infocode;

    private String cdname;

    private String description;

    private String stat;

    private String errmsg;

    private String memo1;

    private String memo2;

    private BigDecimal amt1;

    private BigDecimal amt2;

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno == null ? null : batchno.trim();
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public String getTrandate() {
        return trandate;
    }

    public void setTrandate(String trandate) {
        this.trandate = trandate == null ? null : trandate.trim();
    }

    public String getSysdate() {
        return sysdate;
    }

    public void setSysdate(String sysdate) {
        this.sysdate = sysdate == null ? null : sysdate.trim();
    }

    public Integer getSerseqno() {
        return serseqno;
    }

    public void setSerseqno(Integer serseqno) {
        this.serseqno = serseqno;
    }

    public String getDbrc() {
        return dbrc;
    }

    public void setDbrc(String dbrc) {
        this.dbrc = dbrc == null ? null : dbrc.trim();
    }

    public String getDbaccount() {
        return dbaccount;
    }

    public void setDbaccount(String dbaccount) {
        this.dbaccount = dbaccount == null ? null : dbaccount.trim();
    }

    public String getCbrc() {
        return cbrc;
    }

    public void setCbrc(String cbrc) {
        this.cbrc = cbrc == null ? null : cbrc.trim();
    }

    public String getCraccount() {
        return craccount;
    }

    public void setCraccount(String craccount) {
        this.craccount = craccount == null ? null : craccount.trim();
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid == null ? null : channelid.trim();
    }

    public String getStatcode() {
        return statcode;
    }

    public void setStatcode(String statcode) {
        this.statcode = statcode == null ? null : statcode.trim();
    }

    public String getClsprdcode() {
        return clsprdcode;
    }

    public void setClsprdcode(String clsprdcode) {
        this.clsprdcode = clsprdcode == null ? null : clsprdcode.trim();
    }

    public String getMemocode() {
        return memocode;
    }

    public void setMemocode(String memocode) {
        this.memocode = memocode == null ? null : memocode.trim();
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode == null ? null : infocode.trim();
    }

    public String getCdname() {
        return cdname;
    }

    public void setCdname(String cdname) {
        this.cdname = cdname == null ? null : cdname.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat == null ? null : stat.trim();
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg == null ? null : errmsg.trim();
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1 == null ? null : memo1.trim();
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2 == null ? null : memo2.trim();
    }

    public BigDecimal getAmt1() {
        return amt1;
    }

    public void setAmt1(BigDecimal amt1) {
        this.amt1 = amt1;
    }

    public BigDecimal getAmt2() {
        return amt2;
    }

    public void setAmt2(BigDecimal amt2) {
        this.amt2 = amt2;
    }
}