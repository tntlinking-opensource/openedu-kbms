package org.pfw.framework.aichat.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.pfw.framework.domain.IdEntity;
import org.pfw.framework.domain.security.Role;
import org.pfw.framework.domain.security.User;

public class TAiHsgl extends IdEntity implements java.io.Serializable {
	private String name;
	private Set<TAiZsk> zskSet;
	private String description;
	private String apiurl;
	private User czr;
	private Integer count=0;
	private String czsj;
	private String sfyx;
	private String callbkmb;
	private String beiz;
	
	private List<TAiCsgl> funCallParList = new ArrayList<>();
	private Set<Role> roleSet = new HashSet<Role>();
	
	
	public Set<Role> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	public String getCallbkmb() {
		return callbkmb;
	}
	public void setCallbkmb(String callbkmb) {
		this.callbkmb = callbkmb;
	}
	public List<TAiCsgl> getFunCallParList() {
		return funCallParList;
	}
	public void setFunCallParList(List<TAiCsgl> funCallParList) {
		this.funCallParList = funCallParList;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	public Set<TAiZsk> getZskSet() {
		return zskSet;
	}
	public void setZskSet(Set<TAiZsk> zskSet) {
		this.zskSet = zskSet;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getApiurl() {
		return apiurl;
	}
	public void setApiurl(String apiurl) {
		this.apiurl = apiurl;
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
	public String getBeiz() {
		return beiz;
	}
	public void setBeiz(String beiz) {
		this.beiz = beiz;
	}
	
}
