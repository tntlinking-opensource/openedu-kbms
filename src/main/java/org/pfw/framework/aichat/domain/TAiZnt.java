package org.pfw.framework.aichat.domain;

import java.util.HashSet;
import java.util.Set;

import org.pfw.framework.domain.IdEntity;
import org.pfw.framework.domain.security.Role;
import org.pfw.framework.xtgl.domain.Dict;

public class TAiZnt extends IdEntity implements java.io.Serializable {
	private String zntmc;
	private String zntdm;
	private Set<TAiZsk> zskSet;
	private String promptWords;
	private Set<Role> roleSet = new HashSet<Role>();
	private Double source;
	private Integer dldhs=0;
	private Dict ssfl;
	private String tupianName;
	private String tupianPath;
	private String fbt;
	
	private Integer usermaxTokens;
	private Integer aimaxTokens;
	
	private String jies;
	private Double sxh;
	private String sftj;
	private String sfmrznt;
	private String sfyx;
	
	
	 
	public String getZntdm() {
		return zntdm;
	}
	public void setZntdm(String zntdm) {
		this.zntdm = zntdm;
	}
	public String getPromptWords() {
		return promptWords;
	}
	public void setPromptWords(String promptWords) {
		this.promptWords = promptWords;
	}
	public Integer getDldhs() {
		return dldhs;
	}
	public void setDldhs(Integer dldhs) {
		this.dldhs = dldhs;
	}
	public Set<Role> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	public Double getSource() {
		return source;
	}
	public void setSource(Double source) {
		this.source = source;
	}
	public String getSfmrznt() {
		return sfmrznt;
	}
	public void setSfmrznt(String sfmrznt) {
		this.sfmrznt = sfmrznt;
	}
	public Dict getSsfl() {
		return ssfl;
	}
	public void setSsfl(Dict ssfl) {
		this.ssfl = ssfl;
	}
	public Double getSxh() {
		return sxh;
	}
	public void setSxh(Double sxh) {
		this.sxh = sxh;
	}
	public String getSftj() {
		return sftj;
	}
	public void setSftj(String sftj) {
		this.sftj = sftj;
	}
	public String getFbt() {
		return fbt;
	}
	public void setFbt(String fbt) {
		this.fbt = fbt;
	}
	public Integer getUsermaxTokens() {
		return usermaxTokens;
	}
	public void setUsermaxTokens(Integer usermaxTokens) {
		this.usermaxTokens = usermaxTokens;
	}
	public Integer getAimaxTokens() {
		return aimaxTokens;
	}
	public void setAimaxTokens(Integer aimaxTokens) {
		this.aimaxTokens = aimaxTokens;
	}
	public String getZntmc() {
		return zntmc;
	}
	public void setZntmc(String zntmc) {
		this.zntmc = zntmc;
	}
	public Set<TAiZsk> getZskSet() {
		return zskSet;
	}
	public void setZskSet(Set<TAiZsk> zskSet) {
		this.zskSet = zskSet;
	}
	public String getTupianName() {
		return tupianName;
	}
	public void setTupianName(String tupianName) {
		this.tupianName = tupianName;
	}
	public String getTupianPath() {
		return tupianPath;
	}
	public void setTupianPath(String tupianPath) {
		this.tupianPath = tupianPath;
	}
	public String getJies() {
		return jies;
	}
	public void setJies(String jies) {
		this.jies = jies;
	}
	public String getSfyx() {
		return sfyx;
	}
	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}
	
	
	
}
