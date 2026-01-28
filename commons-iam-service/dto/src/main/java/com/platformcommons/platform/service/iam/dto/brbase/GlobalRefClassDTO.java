package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class GlobalRefClassDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String classCode;
	
	private String name;
	
	public GlobalRefClassDTO() {
		
	}
	public GlobalRefClassDTO(String classCode) {
		this.classCode = classCode;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	@Override
	public String toString() {
		return "GlobalRefClassDTO [classCode=" + classCode + ", name=" + name
				+ "]";
	}
	
}
