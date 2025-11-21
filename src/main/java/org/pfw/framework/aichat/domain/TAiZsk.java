package org.pfw.framework.aichat.domain;

import org.pfw.framework.domain.IdEntity;
import org.pfw.framework.domain.security.User;

public class TAiZsk extends IdEntity implements java.io.Serializable {
	private String zskmc;
	private String zskdm;
	private Double sxh;
	private String zskms;
	private User czr;
	private String czsj;
	
	private String status;
	//启用、弃用？
	private String sfyx;
	
	//文档数量
	private String wdsl="0";
	private String zfsl="0";
	
	
	
	
	public String getWdsl() {
		return wdsl;
	}
	public void setWdsl(String wdsl) {
		this.wdsl = wdsl;
	}
	public String getZfsl() {
		return zfsl;
	}
	public void setZfsl(String zfsl) {
		this.zfsl = zfsl;
	}
	public String getCzsj() {
		return czsj;
	}
	public void setCzsj(String czsj) {
		this.czsj = czsj;
	}
	public User getCzr() {
		return czr;
	}
	public void setCzr(User czr) {
		this.czr = czr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getZskmc() {
		return zskmc;
	}
	public void setZskmc(String zskmc) {
		this.zskmc = zskmc;
	}
	public String getZskdm() {
		return zskdm;
	}
	public void setZskdm(String zskdm) {
		this.zskdm = zskdm;
	}
	public Double getSxh() {
		return sxh;
	}
	public void setSxh(Double sxh) {
		this.sxh = sxh;
	}
	public String getZskms() {
		return zskms;
	}
	public void setZskms(String zskms) {
		this.zskms = zskms;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	
}
