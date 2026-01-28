package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@JsonInclude(value = Include.NON_NULL)
@ApiModel("UserContactDTO")
@NoArgsConstructor
public class UserContactDTO extends BaseDTO implements Serializable {

	private Integer id;

	@ApiModelProperty(value = "user primary contact")
	private Boolean primaryContact;

	@NotNull
	@Valid
	@ApiModelProperty(required = true)
	private ContactDTO contact;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(Boolean primaryContact) {
		this.primaryContact = primaryContact;
	}

	public ContactDTO getContact() {
		return contact;
	}

	public void setContact(ContactDTO contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "UserContactDTO [id = " + id + ",primaryContact = " + primaryContact + " ,contact = "
				+ contact + "]";
	}

	@Builder
	public UserContactDTO(String aliasId, String notes, Integer id, Boolean primaryContact, ContactDTO contact) {
		super(aliasId, notes);
		this.id = id;
		this.primaryContact = primaryContact;
		this.contact = contact;
	}
}
