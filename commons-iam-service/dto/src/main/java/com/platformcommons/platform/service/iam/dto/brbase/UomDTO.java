package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(value = Include.NON_NULL)
public class UomDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal baseConversion;

	@NotNull(message = "UomDTO#code is required")
	@ApiModelProperty(required = true)
	private String code;

	private String description;

	private Boolean isBase;

	@ApiModelProperty(value = "GREF class=GREF.MEASUREMENT_TYPE")
	private GlobalRefDataDTO measurementType;

	private String symbol;

	private String unitName;
	
	@Valid
	private List<MLTextDTO> unitNameML;


	public List<MLTextDTO> getUnitNameML() {
		return unitNameML;
	}

	public void setUnitNameML(List<MLTextDTO> unitNameML) {
		this.unitNameML = unitNameML;
	}

	/**
	 * @return the baseConversion
	 */
	public BigDecimal getBaseConversion() {
		return baseConversion;
	}

	/**
	 * @param baseConversion
	 *            the baseConversion to set
	 */
	public void setBaseConversion(BigDecimal baseConversion) {
		this.baseConversion = baseConversion;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the measurementType
	 */
	public GlobalRefDataDTO getMeasurementType() {
		return measurementType;
	}

	public Boolean getIsBase() {
		return isBase;
	}

	public void setIsBase(Boolean isBase) {
		this.isBase = isBase;
	}

	/**
	 * @param measurementType
	 *            the measurementType to set
	 */
	public void setMeasurementType(GlobalRefDataDTO measurementType) {
		this.measurementType = measurementType;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * @param unitName
	 *            the unitName to set
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Override
	public String toString() {
		return "UomDTO [baseConversion = " + baseConversion + ",code = " + code + ",description = " + description
				+ ",isBase = " + isBase + ",measurementType = " + measurementType + ",symbol = " + symbol
				+ ",unitName = " + unitName + "]";
	}
}
