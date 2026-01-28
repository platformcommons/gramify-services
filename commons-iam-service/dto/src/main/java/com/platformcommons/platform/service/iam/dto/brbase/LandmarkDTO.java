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
public class LandmarkDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// Id of Landmark
	@NotNull(message = "LandmarkDTO#id is required")
	@ApiModelProperty(required = true)
	private Integer id;

	// Name of Landmark
	private String name;

	// address of Landmark
	@NotNull(message = "LandmarkDTO#address is required")
	@ApiModelProperty(required = true)
	@Valid
	private AddressDTO address;

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
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public AddressDTO getAddress() {
		return this.address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "LandmarkDTO [id = " + id + ",name = " + name + ",address = " + address + "]";
	}
}