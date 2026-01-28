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
public class PersonInsuranceDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// PolicyNumber of PersonInsurance
	private String policyNumber;

	// InsuranceType of PersonInsurance
	@Valid
	@ApiModelProperty(value = "GREF class=GREF.INSURANCE_TYPE")
	private GlobalRefDataDTO insuranceType;

	// Name of PersonInsurance
	private String name;

	// Id of PersonInsurance
	@NotNull(message = "PersonInsuranceDTO# id is required")
	@ApiModelProperty(required = true)
	private Integer id;

	/**
	 * @return the policyNumber
	 */
	public String getPolicyNumber() {
		return this.policyNumber;
	}

	/**
	 * @param policyNumber
	 *            the policyNumber to set
	 */
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public GlobalRefDataDTO getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(GlobalRefDataDTO insuranceType) {
		this.insuranceType = insuranceType;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonInsuranceDTO other = (PersonInsuranceDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonInsuranceDTO [policyNumber = " + policyNumber + ",insuranceType = " + insuranceType + ",name = "
				+ name + ",id = " + id + "]";
	}
}