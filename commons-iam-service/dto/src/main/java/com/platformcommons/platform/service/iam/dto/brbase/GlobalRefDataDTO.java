package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
@ApiModel("GlobalRefDataDTO")
@Data
public class GlobalRefDataDTO extends BaseDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="data code", required=true)
	@NotNull(message="GlobalRefDataDTO# dataCode is mandatory")
	private String dataCode;

	private String label;
	
	private GlobalRefClassDTO globalRefClass;
	
	private LanguageDTO language;

	
	public GlobalRefDataDTO() {
		super();
	}

	public GlobalRefDataDTO(String dataCode) {
		super();
		this.dataCode = dataCode;
	}

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

	public GlobalRefClassDTO getGlobalRefClass() {
		return globalRefClass;
	}

	public void setGlobalRefClass(GlobalRefClassDTO globalRefClass) {
		this.globalRefClass = globalRefClass;
	}

	public LanguageDTO getLanguage() {
		return language;
	}

	public void setLanguage(LanguageDTO language) {
		this.language = language;
	}

	@Builder
	public GlobalRefDataDTO(String aliasId, String notes, String dataCode, String label, GlobalRefClassDTO globalRefClass, LanguageDTO language) {
		super(aliasId, notes);
		this.dataCode = dataCode;
		this.label = label;
		this.globalRefClass = globalRefClass;
		this.language = language;
	}

	@Override
	public String toString() {
		return "GlobalRefDataDTO [dataCode=" + dataCode + ", label=" + label
				+ ", globalRefClass=" + globalRefClass + ", language="
				+ language + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataCode == null) ? 0 : dataCode.hashCode());
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GlobalRefDataDTO other = (GlobalRefDataDTO) obj;
		if (dataCode == null) {
			if (other.dataCode != null)
				return false;
		} else if (!dataCode.equals(other.dataCode))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		return true;
	}

	@Builder
	public GlobalRefDataDTO(String dataCode, String label, GlobalRefClassDTO globalRefClass, LanguageDTO language) {
		this.dataCode = dataCode;
		this.label = label;
		this.globalRefClass = globalRefClass;
		this.language = language;
	}
}
