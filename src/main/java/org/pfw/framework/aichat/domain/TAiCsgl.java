package org.pfw.framework.aichat.domain;

import org.pfw.framework.domain.IdEntity;

public class TAiCsgl extends IdEntity implements java.io.Serializable {
	private TAiHsgl sshs;
	private String name;
	private String type;
	private String description;
	private String required;
	
	public TAiHsgl getSshs() {
		return sshs;
	}
	public void setSshs(TAiHsgl sshs) {
		this.sshs = sshs;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	
	
}
