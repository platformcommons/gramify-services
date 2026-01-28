package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(value = Include.NON_NULL)
public class TenantRefClassDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "TenantRefClass#classCode is required")
	@ApiModelProperty(required = true)
	private String classCode;

	@NotNull(message = "TenantRefClass#name is required")
	@ApiModelProperty(required = true)
	private String name;

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
		return "TenantRefClassDTO [classCode = " + classCode + ",name = " + name + "]";
	}
}
