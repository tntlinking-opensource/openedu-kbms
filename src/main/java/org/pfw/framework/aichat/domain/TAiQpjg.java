package org.pfw.framework.aichat.domain;

import org.pfw.framework.domain.IdEntity;

public class TAiQpjg extends IdEntity implements java.io.Serializable {
	private TAiWdxx sswdxx;
	private String biaot;
	private String qpid;
	private String qpnr;
	private Integer tokens;
	private String sfxl="0";
	
	
	public String getSfxl() {
		return sfxl;
	}
	public void setSfxl(String sfxl) {
		this.sfxl = sfxl;
	}
	public String getQpid() {
		return qpid;
	}
	public void setQpid(String qpid) {
		this.qpid = qpid;
	}
	public TAiWdxx getSswdxx() {
		return sswdxx;
	}
	public void setSswdxx(TAiWdxx sswdxx) {
		this.sswdxx = sswdxx;
	}
	public String getBiaot() {
		return biaot;
	}
	public void setBiaot(String biaot) {
		this.biaot = biaot;
	}
	public String getQpnr() {
		return qpnr;
	}
	public void setQpnr(String qpnr) {
		this.qpnr = qpnr;
	}
	public Integer getTokens() {
		return tokens;
	}
	public void setTokens(Integer tokens) {
		this.tokens = tokens;
	}
}
