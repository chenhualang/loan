package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TPoliceInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1981100492397920252L;

	private Integer id;

    private String custName;

    private String photo;

    private String policeCustName;

    private String servPlace;

    private String address;

    private String mariSign;

    private String eduSign;

    private String birthPlace;

    private String nativePlace;

    private String birthDate;

    private String nation;

    private String sexSign;

    private String idNo;

    private String policeIdNo;

    private String result;
    
    private String territorial;
    
    private Date queryTime;
    
    private Date createTime;
    
    private Date updateTime;
    
    
    
    
    
    

    public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}

	public String getTerritorial() {
		return territorial;
	}

	public void setTerritorial(String territorial) {
		this.territorial = territorial;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public String getPoliceCustName() {
        return policeCustName;
    }

    public void setPoliceCustName(String policeCustName) {
        this.policeCustName = policeCustName == null ? null : policeCustName.trim();
    }

    public String getServPlace() {
        return servPlace;
    }

    public void setServPlace(String servPlace) {
        this.servPlace = servPlace == null ? null : servPlace.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getMariSign() {
        return mariSign;
    }

    public void setMariSign(String mariSign) {
        this.mariSign = mariSign == null ? null : mariSign.trim();
    }

    public String getEduSign() {
        return eduSign;
    }

    public void setEduSign(String eduSign) {
        this.eduSign = eduSign == null ? null : eduSign.trim();
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace == null ? null : birthPlace.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate == null ? null : birthDate.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getSexSign() {
        return sexSign;
    }

    public void setSexSign(String sexSign) {
        this.sexSign = sexSign == null ? null : sexSign.trim();
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public String getPoliceIdNo() {
        return policeIdNo;
    }

    public void setPoliceIdNo(String policeIdNo) {
        this.policeIdNo = policeIdNo == null ? null : policeIdNo.trim();
    }

}