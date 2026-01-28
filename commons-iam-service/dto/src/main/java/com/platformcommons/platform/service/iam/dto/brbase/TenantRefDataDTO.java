package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(value = Include.NON_NULL)
@ApiModel("TenantRefDataDTO")
public class TenantRefDataDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "TenantRefData#dataCode is required")
	@ApiModelProperty(value = "dataCode", required = true)
	private String dataCode;

	// @NotNull(message="TenantRefData#label is required")
	@ApiModelProperty(value = "label", required = false)
	private String label;

	// @NotNull(message="TenantRefData#language is required")
	@Valid
	@ApiModelProperty(required = false)
	private LanguageDTO language;

	// @NotNull(message="TenantRefData#tenantRefClass is required")
	@Valid
	@ApiModelProperty(required = false)
	private TenantRefClassDTO tenantRefClass;

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public TenantRefClassDTO getTenantRefClass() {
		return tenantRefClass;
	}

	public LanguageDTO getLanguage() {
		return language;
	}

	public void setLanguage(LanguageDTO language) {
		this.language = language;
	}

	public void setTenantRefClass(TenantRefClassDTO tenantRefClass) {
		this.tenantRefClass = tenantRefClass;
	}

	@Override
	public String toString() {
		return "TenantRefDataDTO [dataCode = " + dataCode + ",label = " + label + ",language = " + language
				+ ",tenantRefClass = " + tenantRefClass + "]";
	}
}
