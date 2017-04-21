package com.hrbb.loan.pos.service.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.hrbb.loan.pos.util.StringUtil;

/**
 * 尽职调查推送消息.
 * 
 * @author xiongshaogang
 * @version $Id: ApproveDueDiligenceMessage.java, v 0.1 2015年5月6日 下午3:17:12 xiongshaogang Exp $
 */
public class ApproveDueDiligenceMessage implements Serializable{
    private String loanid;
    private String posorg;
    private String poscustname;
    private String regicode;
    private String dealscope;
    private String operaddrRegion;
    private String operaddrdetail;
    private String industrytypeid;
    private String custname;
    private String paperkind;
    private String paperid;
    private String sexsign;
    private String busiyear;
    private String marrsign;
    private String educsign;
    private String childnum;
    private String localhousenum;
    private String otherhousenum;
    private String mobilephone;
    private String tel;
    private String residentRegion;
    private String residentdetail;
    private String contactaddrflag;
    private String weixinno;
    private String qqno;
    private String email;
    private String familycustname;
    private String familypaperkind;
    private String familypaperid;
    private String matemobilephone;
    private String relacustname;
    private String relakind;
    private String relamobilephone;
    private String relatel;
    private String imagefilepackagename;
    private String apptcapi;
    private String retukind;
    private String bankaccno;
    private String bankid;
    private String operid;
    private String begindate;
    private String duedilino;
    private String duedilistaffid;
    private String duedilitaskno;
    private String preappmaxcred;
    private String preapptterm;
    private String interate;
    private String duedilifinishdate;
    
    public String getLoanid() {
        return loanid;
    }
    public void setLoanid(String loanid) {
        this.loanid = loanid;
    }
    public String getPosorg() {
        return posorg;
    }
    public void setPosorg(String posorg) {
        this.posorg = posorg;
    }
    public String getPoscustname() {
        return poscustname;
    }
    public void setPoscustname(String poscustname) {
        this.poscustname = poscustname;
    }
    public String getRegicode() {
        return regicode;
    }
    public void setRegicode(String regicode) {
        this.regicode = regicode;
    }
    public String getDealscope() {
        return dealscope;
    }
    public void setDealscope(String dealscope) {
        this.dealscope = dealscope;
    }
    public String getOperaddrRegion() {
        return operaddrRegion;
    }
    public void setOperaddrRegion(String operaddrRegion) {
        this.operaddrRegion = operaddrRegion;
    }
    public String getOperaddrdetail() {
        return operaddrdetail;
    }
    public void setOperaddrdetail(String operaddrdetail) {
        this.operaddrdetail = operaddrdetail;
    }
    public String getIndustrytypeid() {
        return industrytypeid;
    }
    public void setIndustrytypeid(String industrytypeid) {
        this.industrytypeid = industrytypeid;
    }
    public String getCustname() {
        return custname;
    }
    public void setCustname(String custname) {
        this.custname = custname;
    }
    public String getPaperkind() {
        return paperkind;
    }
    public void setPaperkind(String paperkind) {
        this.paperkind = paperkind;
    }
    public String getPaperid() {
        return paperid;
    }
    public void setPaperid(String paperid) {
        this.paperid = paperid;
    }
    public String getSexsign() {
        return sexsign;
    }
    public void setSexsign(String sexsign) {
        this.sexsign = sexsign;
    }
    public String getBusiyear() {
        return busiyear;
    }
    public void setBusiyear(String busiyear) {
        this.busiyear = busiyear;
    }
    public String getMarrsign() {
        return marrsign;
    }
    public void setMarrsign(String marrsign) {
        this.marrsign = marrsign;
    }
    public String getEducsign() {
        return educsign;
    }
    public void setEducsign(String educsign) {
        this.educsign = educsign;
    }
    public String getChildnum() {
        return childnum;
    }
    public void setChildnum(String childnum) {
        this.childnum = childnum;
    }
    public String getLocalhousenum() {
        return localhousenum;
    }
    public void setLocalhousenum(String localhousenum) {
        this.localhousenum = localhousenum;
    }
    public String getOtherhousenum() {
        return otherhousenum;
    }
    public void setOtherhousenum(String otherhousenum) {
        this.otherhousenum = otherhousenum;
    }
    public String getMobilephone() {
        return mobilephone;
    }
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getResidentRegion() {
        return residentRegion;
    }
    public void setResidentRegion(String residentRegion) {
        this.residentRegion = residentRegion;
    }
    public String getResidentdetail() {
        return residentdetail;
    }
    public void setResidentdetail(String residentdetail) {
        this.residentdetail = residentdetail;
    }
    public String getContactaddrflag() {
        return contactaddrflag;
    }
    public void setContactaddrflag(String contactaddrflag) {
        this.contactaddrflag = contactaddrflag;
    }
    public String getWeixinno() {
        return weixinno;
    }
    public void setWeixinno(String weixinno) {
        this.weixinno = weixinno;
    }
    public String getQqno() {
        return qqno;
    }
    public void setQqno(String qqno) {
        this.qqno = qqno;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFamilycustname() {
        return familycustname;
    }
    public void setFamilycustname(String familycustname) {
        this.familycustname = familycustname;
    }
    public String getFamilypaperkind() {
        return familypaperkind;
    }
    public void setFamilypaperkind(String familypaperkind) {
        this.familypaperkind = familypaperkind;
    }
    public String getFamilypaperid() {
        return familypaperid;
    }
    public void setFamilypaperid(String familypaperid) {
        this.familypaperid = familypaperid;
    }
    public String getMatemobilephone() {
        return matemobilephone;
    }
    public void setMatemobilephone(String matemobilephone) {
        this.matemobilephone = matemobilephone;
    }
    public String getRelacustname() {
        return relacustname;
    }
    public void setRelacustname(String relacustname) {
        this.relacustname = relacustname;
    }
    public String getRelakind() {
        return relakind;
    }
    public void setRelakind(String relakind) {
        this.relakind = relakind;
    }
    public String getRelamobilephone() {
        return relamobilephone;
    }
    public void setRelamobilephone(String relamobilephone) {
        this.relamobilephone = relamobilephone;
    }
    public String getRelatel() {
        return relatel;
    }
    public void setRelatel(String relatel) {
        this.relatel = relatel;
    }
    public String getImagefilepackagename() {
        return imagefilepackagename;
    }
    public void setImagefilepackagename(String imagefilepackagename) {
        this.imagefilepackagename = imagefilepackagename;
    }
    public String getApptcapi() {
        return apptcapi;
    }
    public void setApptcapi(String apptcapi) {
        this.apptcapi = apptcapi;
    }
    public String getRetukind() {
        return retukind;
    }
    public void setRetukind(String retukind) {
        this.retukind = retukind;
    }
    public String getBankaccno() {
        return bankaccno;
    }
    public void setBankaccno(String bankaccno) {
        this.bankaccno = bankaccno;
    }
    public String getBankid() {
        return bankid;
    }
    public void setBankid(String bankid) {
        this.bankid = bankid;
    }
    public String getOperid() {
        return operid;
    }
    public void setOperid(String operid) {
        this.operid = operid;
    }
    public String getBegindate() {
        return begindate;
    }
    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }
    public String getDuedilino() {
        return duedilino;
    }
    public void setDuedilino(String duedilino) {
        this.duedilino = duedilino;
    }
    public String getDuedilistaffid() {
        return duedilistaffid;
    }
    public void setDuedilistaffid(String duedilistaffid) {
        this.duedilistaffid = duedilistaffid;
    }
    public String getDuedilitaskno() {
        return duedilitaskno;
    }
    public void setDuedilitaskno(String duedilitaskno) {
        this.duedilitaskno = duedilitaskno;
    }
    public String getPreappmaxcred() {
        return preappmaxcred;
    }
    public void setPreappmaxcred(String preappmaxcred) {
        this.preappmaxcred = preappmaxcred;
    }
    public String getPreapptterm() {
        return preapptterm;
    }
    public void setPreapptterm(String preapptterm) {
        this.preapptterm = preapptterm;
    }
    public String getInterate() {
        return interate;
    }
    public void setInterate(String interate) {
        this.interate = interate;
    }
    public String getDuedilifinishdate() {
        return duedilifinishdate;
    }
    public void setDuedilifinishdate(String duedilifinishdate) {
        this.duedilifinishdate = duedilifinishdate;
    }
    
    public Map<String, String> getDataMap() {
        
        Map<String, String> jsonMap = Maps.newHashMap();
        jsonMap.put("loanid", StringUtil.trimToEmpty(getLoanid()));
        jsonMap.put("posorg", StringUtil.trimToEmpty(getPosorg()));
        jsonMap.put("poscustname", StringUtil.trimToEmpty(getPoscustname()));
        jsonMap.put("regicode", StringUtil.trimToEmpty(getRegicode()));
        jsonMap.put("dealscope", StringUtil.trimToEmpty(getDealscope()));
        jsonMap.put("operaddrRegion", StringUtil.trimToEmpty(getOperaddrRegion()));
        jsonMap.put("operaddrdetail", StringUtil.trimToEmpty(getOperaddrdetail()));
        jsonMap.put("industrytypeid", StringUtil.trimToEmpty(getIndustrytypeid()));
        jsonMap.put("custname", StringUtil.trimToEmpty(getCustname()));
        jsonMap.put("paperkind", StringUtil.trimToEmpty(getPaperkind()));
        jsonMap.put("paperid", StringUtil.trimToEmpty(getPaperid()));
        jsonMap.put("sexsign", StringUtil.trimToEmpty(getSexsign()));
        jsonMap.put("busiyear", StringUtil.trimToEmpty(getBusiyear()));
        jsonMap.put("marrsign", StringUtil.trimToEmpty(getMarrsign()));
        jsonMap.put("educsign", StringUtil.trimToEmpty(getEducsign()));
        jsonMap.put("childnum", StringUtil.trimToEmpty(getChildnum()));
        jsonMap.put("localhousenum", StringUtil.trimToEmpty(getLocalhousenum()));
        jsonMap.put("otherhousenum", StringUtil.trimToEmpty(getOtherhousenum()));
        jsonMap.put("mobilephone", StringUtil.trimToEmpty(getMobilephone()));
        jsonMap.put("tel", StringUtil.trimToEmpty(getTel()));
        jsonMap.put("residentRegion", StringUtil.trimToEmpty(getResidentRegion()));
        jsonMap.put("residentdetail", StringUtil.trimToEmpty(getResidentdetail()));
        jsonMap.put("contactaddrflag", StringUtil.trimToEmpty(getContactaddrflag()));
        jsonMap.put("weixinno", StringUtil.trimToEmpty(getWeixinno()));
        jsonMap.put("qqno", StringUtil.trimToEmpty(getQqno()));
        jsonMap.put("email", StringUtil.trimToEmpty(getEmail()));
        jsonMap.put("familycustname", StringUtil.trimToEmpty(getFamilycustname()));
        jsonMap.put("familypaperkind", StringUtil.trimToEmpty(getFamilypaperkind()));
        jsonMap.put("familypaperid", StringUtil.trimToEmpty(getFamilypaperid()));
        jsonMap.put("matemobilephone", StringUtil.trimToEmpty(getMatemobilephone()));
        jsonMap.put("relacustname", StringUtil.trimToEmpty(getRelacustname()));
        jsonMap.put("relakind", StringUtil.trimToEmpty(getRelakind()));
        jsonMap.put("relamobilephone", StringUtil.trimToEmpty(getRelamobilephone()));
        jsonMap.put("relatel", StringUtil.trimToEmpty(getRelatel()));
        jsonMap.put("imagefilepackagename", StringUtil.trimToEmpty(getImagefilepackagename()));
        jsonMap.put("apptcapi", StringUtil.trimToEmpty(getApptcapi()));
        jsonMap.put("retukind", StringUtil.trimToEmpty(getRetukind()));
        jsonMap.put("bankaccno", StringUtil.trimToEmpty(getBankaccno()));
        jsonMap.put("bankid", StringUtil.trimToEmpty(getBankid()));
        jsonMap.put("operid", StringUtil.trimToEmpty(getOperid()));
        jsonMap.put("begindate", StringUtil.trimToEmpty(getBegindate()));
        jsonMap.put("duedilino", StringUtil.trimToEmpty(getDuedilino())); // 尽职调查机构号
        jsonMap.put("duedilistaffid", StringUtil.trimToEmpty(getDuedilistaffid())); // 尽职调查人员 
        jsonMap.put("duedilitaskno", StringUtil.trimToEmpty(getDuedilitaskno())); // 尽职调查任务编号
        jsonMap.put("preappmaxcred", StringUtil.trimToEmpty(getPreappmaxcred())); // 预授信额度
        jsonMap.put("preapptterm", StringUtil.trimToEmpty(getPreapptterm()));   // 预授信期限
        jsonMap.put("interate", StringUtil.trimToEmpty(getInterate()));  // 贷款利率
        jsonMap.put("duedilifinishdate", StringUtil.trimToEmpty(getDuedilifinishdate())); // 尽职调查完成时间
        
        return jsonMap;
    }
    
    public String toJSONString() {
        Map<String, String> jsonMap = Maps.newHashMap();
        jsonMap.put("loanid", StringUtil.trimToEmpty(getLoanid()));
        jsonMap.put("posorg", StringUtil.trimToEmpty(getPosorg()));
        jsonMap.put("poscustname", StringUtil.trimToEmpty(getPoscustname()));
        jsonMap.put("regicode", StringUtil.trimToEmpty(getRegicode()));
        jsonMap.put("dealscope", StringUtil.trimToEmpty(getDealscope()));
        jsonMap.put("operaddrRegion", StringUtil.trimToEmpty(getOperaddrRegion()));
        jsonMap.put("operaddrdetail", StringUtil.trimToEmpty(getOperaddrdetail()));
        jsonMap.put("industrytypeid", StringUtil.trimToEmpty(getIndustrytypeid()));
        jsonMap.put("custname", StringUtil.trimToEmpty(getCustname()));
        jsonMap.put("paperkind", StringUtil.trimToEmpty(getPaperkind()));
        jsonMap.put("paperid", StringUtil.trimToEmpty(getPaperid()));
        jsonMap.put("sexsign", StringUtil.trimToEmpty(getSexsign()));
        jsonMap.put("busiyear", StringUtil.trimToEmpty(getBusiyear()));
        jsonMap.put("marrsign", StringUtil.trimToEmpty(getMarrsign()));
        jsonMap.put("educsign", StringUtil.trimToEmpty(getEducsign()));
        jsonMap.put("childnum", StringUtil.trimToEmpty(getChildnum()));
        jsonMap.put("localhousenum", StringUtil.trimToEmpty(getLocalhousenum()));
        jsonMap.put("otherhousenum", StringUtil.trimToEmpty(getOtherhousenum()));
        jsonMap.put("mobilephone", StringUtil.trimToEmpty(getMobilephone()));
        jsonMap.put("tel", StringUtil.trimToEmpty(getTel()));
        jsonMap.put("residentRegion", StringUtil.trimToEmpty(getResidentRegion()));
        jsonMap.put("residentdetail", StringUtil.trimToEmpty(getResidentdetail()));
        jsonMap.put("contactaddrflag", StringUtil.trimToEmpty(getContactaddrflag()));
        jsonMap.put("weixinno", StringUtil.trimToEmpty(getWeixinno()));
        jsonMap.put("qqno", StringUtil.trimToEmpty(getQqno()));
        jsonMap.put("email", StringUtil.trimToEmpty(getEmail()));
        jsonMap.put("familycustname", StringUtil.trimToEmpty(getFamilycustname()));
        jsonMap.put("familypaperkind", StringUtil.trimToEmpty(getFamilypaperkind()));
        jsonMap.put("familypaperid", StringUtil.trimToEmpty(getFamilypaperid()));
        jsonMap.put("matemobilephone", StringUtil.trimToEmpty(getMatemobilephone()));
        jsonMap.put("relacustname", StringUtil.trimToEmpty(getRelacustname()));
        jsonMap.put("relakind", StringUtil.trimToEmpty(getRelakind()));
        jsonMap.put("relamobilephone", StringUtil.trimToEmpty(getRelamobilephone()));
        jsonMap.put("relatel", StringUtil.trimToEmpty(getRelatel()));
        jsonMap.put("imagefilepackagename", StringUtil.trimToEmpty(getImagefilepackagename()));
        jsonMap.put("apptcapi", StringUtil.trimToEmpty(getApptcapi()));
        jsonMap.put("retukind", StringUtil.trimToEmpty(getRetukind()));
        jsonMap.put("bankaccno", StringUtil.trimToEmpty(getBankaccno()));
        jsonMap.put("bankid", StringUtil.trimToEmpty(getBankid()));
        jsonMap.put("operid", StringUtil.trimToEmpty(getOperid()));
        jsonMap.put("begindate", StringUtil.trimToEmpty(getBegindate()));
        jsonMap.put("duedilino", StringUtil.trimToEmpty(getDuedilino())); // 尽职调查机构号
        jsonMap.put("duedilistaffid", StringUtil.trimToEmpty(getDuedilistaffid())); // 尽职调查人员 
        jsonMap.put("duedilitaskno", StringUtil.trimToEmpty(getDuedilitaskno())); // 尽职调查任务编号
        jsonMap.put("preappmaxcred", StringUtil.trimToEmpty(getPreappmaxcred())); // 预授信额度
        jsonMap.put("preapptterm", StringUtil.trimToEmpty(getPreapptterm()));   // 预授信期限
        jsonMap.put("interate", StringUtil.trimToEmpty(getInterate()));  // 贷款利率
        jsonMap.put("duedilifinishdate", StringUtil.trimToEmpty(getDuedilifinishdate())); // 尽职调查完成时间
        return JSON.toJSONString(jsonMap);
    }
    
    public String toURLParam() {
        return getURLParam(getDataMap(), false, null);
    }
    
    public String getURLParam(Map<String, String> map, boolean isSort, Set<String> removeKey) {
        StringBuffer param = new StringBuffer();
        List<String> msgList = new ArrayList<String>();
        for (Map.Entry<String, String> en : map.entrySet()) {
            String key = en.getKey();
            String value = en.getValue();
            if (removeKey != null && removeKey.contains(key)) {
                continue;
            }
            msgList.add(key + "=" + StringUtil.toEmpty(value));
        }

        if (isSort) {
            // 排序
            Collections.sort(msgList);
        }

        for (int i = 0; i < msgList.size(); i++) {
            String msg = (String) msgList.get(i);
            if (i > 0) {
                param.append("&");
            }
            param.append(msg);
        }

        return param.toString();
    }
}
