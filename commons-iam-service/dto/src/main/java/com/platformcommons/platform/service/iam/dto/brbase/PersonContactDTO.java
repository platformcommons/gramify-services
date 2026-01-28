package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

@JsonInclude(value = Include.NON_NULL)
public class PersonContactDTO extends BaseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "PersonContact#Id is required")
	@ApiModelProperty(required=true)
	private Integer id;
	
	private Boolean isPrimary;
	
	@NotNull(message="PersonContact#contact is required")
	@Valid
	@ApiModelProperty(required=true)
	private ContactDTO contact;

	public PersonContactDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public ContactDTO getContact() {
		return contact;
	}

	public void setContact(ContactDTO contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "PersonContactDTO [id=" + id + ", isPrimary=" + isPrimary
				+ ", contact=" + contact + "]";
	}

	@Builder
	public PersonContactDTO(String aliasId, String notes, Integer id, Boolean isPrimary, ContactDTO contact) {
		super(aliasId, notes);
		this.id = id;
		this.isPrimary = isPrimary;
		this.contact = contact;
	}
}
