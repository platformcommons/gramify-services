package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * Generated class Will not be over written
 */
@JsonInclude(value = Include.NON_NULL)
public class MLTextDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	// Text of MLText
	@NotNull(message="text is required")
	@ApiModelProperty(required=true)
	private String text;
	
	// Id of MLText
	@NotNull(message="MLText id is required")
	@ApiModelProperty(required=true)
	private Integer id;

	// languageCode of MLText
	@Valid
	private LanguageDTO languageCode;

	/**
	 * @return the text
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the languageCode
	 */
	public LanguageDTO getLanguageCode() {
		return this.languageCode;
	}

	/**
	 * @param languageCode
	 *            the languageCode to set
	 */
	public void setLanguageCode(LanguageDTO languageCode) {
		this.languageCode = languageCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		MLTextDTO other = (MLTextDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MLTextDTO [text=" + text + ", appUpdatedDateTime="
				+ appUpdatedDateTime + ", appCreatedDateTime="
				+ appCreatedDateTime + ", id=" + id + ", languageCode="
				+ languageCode + "]";
	}

}