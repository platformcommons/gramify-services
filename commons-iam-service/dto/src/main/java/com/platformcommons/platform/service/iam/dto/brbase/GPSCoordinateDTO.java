package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * Generated class Will not be over written
 */
@JsonInclude(value = Include.NON_NULL)
public class GPSCoordinateDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	// Id of GPSCoordinate
	@ApiModelProperty(required = true)
	@NotNull(message = "GPSCoordinateDTO# id cannot be null")
	private Integer id;

	// Latitude of GPSCoordinate
	@ApiModelProperty(required = true)
	@NotNull(message = "GPSCoordinateDTO# latitude cannot be null")
	@Min(-90)
	@Max(90)
	private Double latitude;

	// Longitude of GPSCoordinate
	@ApiModelProperty(required = true)
	@NotNull(message = "GPSCoordinateDTO# longitude cannot be null")
	@Min(-180)
	@Max(180)
	private Double longitude;

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
	 * @return the latitude
	 */
	public Double getLatitude() {
		return this.latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return this.longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "GPSCoordinateDTO [id = " + id + ",latitude = " + latitude + ",longitude = " + longitude + "]";
	}
}