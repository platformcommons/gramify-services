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

@JsonInclude(value = Include.NON_NULL)
@ApiModel("UserAddressDTO")
@NoArgsConstructor
public class UserAddressDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@ApiModelProperty(value = "user primary address")
	private Boolean primaryAddress;

	@NotNull
	@ApiModelProperty(value = "GREF.USER_ADDRESS_TYPE", required = true)
	private GlobalRefDataDTO userAddressType;

	@NotNull
	@Valid
	@ApiModelProperty(required = true)
	private AddressDTO address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getPrimaryAddress() {
		return primaryAddress;
	}

	public void setPrimaryAddress(Boolean primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	public GlobalRefDataDTO getUserAddressType() {
		return userAddressType;
	}

	public void setUserAddressType(GlobalRefDataDTO userAddressType) {
		this.userAddressType = userAddressType;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	@Builder
	public UserAddressDTO(String aliasId, String notes, Integer id, Boolean primaryAddress, GlobalRefDataDTO userAddressType, AddressDTO address) {
		super(aliasId, notes);
		this.id = id;
		this.primaryAddress = primaryAddress;
		this.userAddressType = userAddressType;
		this.address = address;
	}

	@Override
	public String toString() {
		return "UserAddressDTO [id=" + id + ", primaryAddress=" + primaryAddress + ", userAddressType="
				+ userAddressType + ", addressdto=" + address + ", userLoginName=" + ", tenantLoginName=" + "]";
	}

}
