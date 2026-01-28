package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(value = Include.NON_NULL)
public class AddressValidityDTO extends BaseDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Date endDate;
	
	private Date startDate;
	
	@ApiModelProperty(required=true)
	private Boolean validated;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Boolean getValidated() {
		return validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	@Override
	public String toString() {
		return "AddressValidityDTO [id=" + id + ", endDate=" + endDate
				+ ", startDate=" + startDate + ", validated=" + validated + "]";
	}

	
}
