package org.pfw.framework.aichat.domain;

import org.pfw.framework.domain.IdEntity;
import org.pfw.framework.domain.security.User;

public class TAiWdxx extends IdEntity implements java.io.Serializable {
	private TAiZsk sszsk;
	private TAiYhdhjg ssdhjg;

	private String fjwjmName;
	private String fjwjmPath;
	
	private String mc;
	
	private String wjlx;
	private String qpsl="0";
	private String laiy;
	
	private String wjnr;
	private String beiz;
	private String qpzt="2";

	private User czr;
	private String czsj;
	 

	

	
	
	 
	public TAiYhdhjg getSsdhjg() {
		return ssdhjg;
	}
	public void setSsdhjg(TAiYhdhjg ssdhjg) {
		this.ssdhjg = ssdhjg;
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

	public String getQpsl() {
		return qpsl;
	}

	public void setQpsl(String qpsl) {
		this.qpsl = qpsl;
	}

	public String getQpzt() {
		return qpzt;
	}

	public void setQpzt(String qpzt) {
		this.qpzt = qpzt;
	}

	public String getBeiz() {
		return beiz;
	}

	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}

	public TAiZsk getSszsk() {
		return sszsk;
	}

	public void setSszsk(TAiZsk sszsk) {
		this.sszsk = sszsk;
	}

	public String getLaiy() {
		return laiy;
	}

	public void setLaiy(String laiy) {
		this.laiy = laiy;
	}

	

	public String getFjwjmName() {
		return fjwjmName;
	}

	public void setFjwjmName(String fjwjmName) {
		this.fjwjmName = fjwjmName;
	}

	public String getFjwjmPath() {
		return fjwjmPath;
	}

	public void setFjwjmPath(String fjwjmPath) {
		this.fjwjmPath = fjwjmPath;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getWjlx() {
		return wjlx;
	}

	public void setWjlx(String wjlx) {
		this.wjlx = wjlx;
	}
	 
	public String getWjnr() {
		return wjnr;
	}
	public void setWjnr(String wjnr) {
		this.wjnr = wjnr;
	}
	
	
	
	
}
