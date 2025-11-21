package org.pfw.framework.aichat.domain;

import org.pfw.framework.domain.IdEntity;
import org.pfw.framework.domain.security.User;

public class TAiYhdhjg extends IdEntity implements java.io.Serializable {
	private TAiZnt ssznt;
	private TAiYhdhls ssdhls;
//	private String role;
	private String yhtw;
	private String skgc;
	private String zzda;
	private Integer tokens;
	private User czr;
	private String czsj;
	
	private TAiZsk sszsk;
	private String xghyhwt;
	private String xghzzda;
	private String sftz="0";
	private String sfxlh="0";
	
	
	public TAiZsk getSszsk() {
		return sszsk;
	}
	public void setSszsk(TAiZsk sszsk) {
		this.sszsk = sszsk;
	}
	public String getSftz() {
		return sftz;
	}
	public void setSftz(String sftz) {
		this.sftz = sftz;
	}
	public String getXghyhwt() {
		return xghyhwt;
	}
	public void setXghyhwt(String xghyhwt) {
		this.xghyhwt = xghyhwt;
	}
	public String getXghzzda() {
		return xghzzda;
	}
	public void setXghzzda(String xghzzda) {
		this.xghzzda = xghzzda;
	}
	public String getSfxlh() {
		return sfxlh;
	}
	public void setSfxlh(String sfxlh) {
		this.sfxlh = sfxlh;
	}
	public Integer getTokens() {
		return tokens;
	}
	public void setTokens(Integer tokens) {
		this.tokens = tokens;
	}
	public TAiZnt getSsznt() {
		return ssznt;
	}
	public void setSsznt(TAiZnt ssznt) {
		this.ssznt = ssznt;
	}
	public TAiYhdhls getSsdhls() {
		return ssdhls;
	}
	public void setSsdhls(TAiYhdhls ssdhls) {
		this.ssdhls = ssdhls;
	}
//	public String getRole() {
//		return role;
//	}
//	public void setRole(String role) {
//		this.role = role;
//	}
	public String getYhtw() {
		return yhtw;
	}
	public void setYhtw(String yhtw) {
		this.yhtw = yhtw;
	}
	public String getSkgc() {
        return skgc;
	}
	public void setSkgc(String skgc) {
		this.skgc = skgc;
	}
	public String getZzda() {
	    return zzda; // 返回新字符串，不修改原字段
	}
	public void setZzda(String zzda) {
		this.zzda = zzda;
	}
	public User getCzr() {
		return czr;
	}
	public void setCzr(User czr) {
		this.czr = czr;
	}
	public String getCzsj() {
		return czsj;
	}
	public void setCzsj(String czsj) {
		this.czsj = czsj;
	}
	
	
	
}
