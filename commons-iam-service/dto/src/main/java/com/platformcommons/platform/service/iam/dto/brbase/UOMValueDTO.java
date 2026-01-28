package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(value = Include.NON_NULL)
public class UOMValueDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "UOMValueDTO#id is required")
	@ApiModelProperty(required = true)
	private int id;

	@NotNull(message = "UOMValueDTO#value is required")
	@ApiModelProperty(required = true)
	private Double value;

	@NotNull(message = "UOMValueDTO#uom is required")
	@ApiModelProperty(required = true, value = "Uom codes: UOM.LENGTH.M, UOM.LENGTH.KM")
	@Valid
	private UomDTO uom;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * @return the uom
	 */
	public UomDTO getUom() {
		return uom;
	}

	/**
	 * @param uom
	 *            the uom to set
	 */
	public void setUom(UomDTO uom) {
		this.uom = uom;
	}

	@Override
	public String toString() {
		return "UOMValueDTO [id = " + id + ",value = " + value + ",uom = " + uom + "]";
	}
}
