package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

@JsonInclude(value = Include.NON_NULL)
@ApiModel("LanguageDTO")
public class LanguageDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	// @NotNull(message = "Language#language code is required")
	// @Size(min = 3, max = 250, message =
	// "Language#code size should be greater than 2 and less than 45")
	@ApiModelProperty(value="Language code", required=true)
	private String languageCode;

	// @NotNull(message = "Language#language name is required")
	// @Size(min = 3, max = 250, message =
	// "Language#name size should be greater than 3 and less than 45")
	private String language;

	public LanguageDTO() {
		super();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result
				+ ((languageCode == null) ? 0 : languageCode.hashCode());
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
		LanguageDTO other = (LanguageDTO) obj;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (languageCode == null) {
			if (other.languageCode != null)
				return false;
		} else if (!languageCode.equals(other.languageCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LanguageDTO [language=" + language + ", languageCode="
				+ languageCode + "]";
	}

	@Builder
	public LanguageDTO(String languageCode, String language) {
		this.languageCode = languageCode;
		this.language = language;
	}
}
