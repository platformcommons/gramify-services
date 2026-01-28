package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(value = Include.NON_NULL)
@ApiModel("DepartmentDTO")
public class DepartmentDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	@NotNull(message = "Department#departmentType is required")
	@Valid
	@ApiModelProperty(value = "TREF class=TREF.DEPARTMENT_TYPE")
	private TenantRefDataDTO departmentType;

	@NotNull(message = "Department#name is required")
	@Size(min = 3, max = 45, message = "Department#name size should be greater than 3 and less than 45")
	@ApiModelProperty(value = "Department name", required = true)
	private String name;

	// private DepartmentDTO parentDepartment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TenantRefDataDTO getDepartmentType() {
		return departmentType;
	}

	public void setDepartmentType(TenantRefDataDTO departmentType) {
		this.departmentType = departmentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * public DepartmentDTO getParentDepartment() { return parentDepartment; }
	 * 
	 * public void setParentDepartment(DepartmentDTO parentDepartment) {
	 * this.parentDepartment = parentDepartment; }
	 */

	@Override
	public String toString() {
		return "DepartmentDTO [id = " + id + ",departmentType = " + departmentType + ",name = " + name + "]";
	}

}