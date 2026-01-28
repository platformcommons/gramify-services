package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(value = Include.NON_NULL)
public class PersonAddressDTO extends BaseDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull(message = "PersonAddress#Id is required")
	@ApiModelProperty(required=true)
	private Integer id;

	private Boolean isPrimary;

	@Valid
	@ApiModelProperty(value = "GREF.PERSON_ADDRESS_TYPE")
	private GlobalRefDataDTO addressType;

	@NotNull(message = "PersonAddress#address is required")
	@Valid
	@ApiModelProperty(required=true)
	private AddressDTO address;

	public PersonAddressDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GlobalRefDataDTO getAddressType() {
		return addressType;
	}

	public Boolean getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public void setAddressType(GlobalRefDataDTO addressType) {
		this.addressType = addressType;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "PersonAddressDTO [id=" + id + ", isPrimary=" + isPrimary
				+ ", addressType=" + addressType + ", address=" + address + "]";
	}

}
