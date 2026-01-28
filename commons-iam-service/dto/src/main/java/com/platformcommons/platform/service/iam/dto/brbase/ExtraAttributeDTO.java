package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Generated class Will not be over written
 */
@JsonInclude(value = Include.NON_NULL)
public class ExtraAttributeDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	// AttributeValue of ExtraAttributes
	@NotNull
	private String attributeValue;
	// Id of ExtraAttributes
	@NotNull
	private Integer id;

	// valueStr of ExtraAttributes
	@Valid
	private List<MLTextDTO> valueStr;

	// metadata of ExtraAttributes
	@Valid
	@NotNull
	private String metadata;

	// Type of ExtraAttriValidationRules
	@NotNull
	private String attributeType;

	/**
	 * @return the attributeValue
	 */
	public String getAttributeValue() {
		return this.attributeValue;
	}

	/**
	 * @param attributeValue
	 *            the attributeValue to set
	 */
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

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
	 * @return the valueStr
	 */
	public List<MLTextDTO> getValueStr() {
		return this.valueStr;
	}

	/**
	 * @param valueStr
	 *            the valueStr to set
	 */
	public void setValueStr(List<MLTextDTO> valueStr) {
		this.valueStr = valueStr;
	}

	/**
	 * @return the tenant
	 */
	/*
	 * public TenantDTO getTenant() { return this.tenant; }
	 *//**
		 * @param tenant
		 *            the tenant to set
		 *//*
		 * public void setTenant(TenantDTO tenant) { this.tenant = tenant; }
		 */

	/**
	 * @return the metadata
	 */
	public String getMetadata() {
		return this.metadata;
	}

	/**
	 * @param metadata
	 *            the metadata to set
	 */
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	/**
	 * @return the type
	 */
	public String getAttributeType() {
		return attributeType;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	@Override
	public String toString() {
		return "ExtraAttributeDTO [attributeValue = " + attributeValue + ",id = " + id + ",valueStr = " + valueStr
				+ ",metadata = " + metadata + ",attributeType = " + attributeType + "]";
	}
}