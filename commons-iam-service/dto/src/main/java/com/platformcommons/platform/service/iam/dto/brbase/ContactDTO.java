package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor
public class ContactDTO extends BaseDTO implements Serializable{

	private Integer id;

	@Valid
	@ApiModelProperty(required = true, value = "classCode=GREF.CONTACT_TYPE")
	@NotNull(message = "ContactDTO# Contact type cannot be null")
	private GlobalRefDataDTO contactType;

	@NotNull(message = "ContactDTO# Contact value cannot be null")
	@ApiModelProperty(required = true, value = "Value of contact eg: user@domain.com, 98123456")
	private String contactValue;

	@ApiModelProperty(required = false, value = "Is contact verified; default value is false")
	private Boolean verified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GlobalRefDataDTO getContactType() {
		return contactType;
	}

	public void setContactType(GlobalRefDataDTO contactType) {
		this.contactType = contactType;
	}

	public String getContactValue() {
		return contactValue;
	}

	public void setContactValue(String contactValue) {
		this.contactValue = contactValue;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	@Builder
	public ContactDTO(String aliasId, String notes, Integer id, GlobalRefDataDTO contactType, String contactValue, Boolean verified) {
		super(aliasId, notes);
		this.id = id;
		this.contactType = contactType;
		this.contactValue = contactValue;
		this.verified = verified;
	}

	@Override
	public String toString() {
		return "ContactDTO [id=" + id + ", contactType=" + contactType
				+ ", contactValue=" + contactValue + ", verified=" + verified
				+ "]";
	}

}
