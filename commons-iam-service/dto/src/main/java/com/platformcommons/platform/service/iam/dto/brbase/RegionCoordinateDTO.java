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
public class RegionCoordinateDTO extends BaseDTO implements Serializable, Comparable<RegionCoordinateDTO> {

	private static final long serialVersionUID = 1L;
	// Id of RegionCoordinate
	@NotNull(message = "RegionCoordinateDTO#id is required")
	@ApiModelProperty(required = true)
	private Integer id;
	// OrderSequence of RegionCoordinate
	@NotNull(message = "RegionCoordinateDTO#orderSequence is required")
	@ApiModelProperty(required = true)
	private Integer orderSequence;

	// gPSCoordinate of RegionCoordinate
	@Valid
	@NotNull(message = "RegionCoordinateDTO#gPSCoordinate is required")
	@ApiModelProperty(required = true)
	private GPSCoordinateDTO gPSCoordinate;

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
	 * @return the orderSequence
	 */
	public Integer getOrderSequence() {
		return this.orderSequence;
	}

	/**
	 * @param orderSequence
	 *            the orderSequence to set
	 */
	public void setOrderSequence(Integer orderSequence) {
		this.orderSequence = orderSequence;
	}

	/**
	 * @return the gPSCoordinate
	 */
	public GPSCoordinateDTO getgPSCoordinate() {
		return this.gPSCoordinate;
	}

	/**
	 * @param gPSCoordinate
	 *            the gPSCoordinate to set
	 */
	public void setgPSCoordinate(GPSCoordinateDTO gPSCoordinate) {
		this.gPSCoordinate = gPSCoordinate;
	}

	@Override
	public int compareTo(RegionCoordinateDTO r) {
		return this.orderSequence.compareTo(r.orderSequence);
	}

	@Override
	public String toString() {
		return "RegionCoordinateDTO [id = " + id + ",orderSequence = " + orderSequence + ",gPSCoordinate = "
				+ gPSCoordinate + "]";
	}
}