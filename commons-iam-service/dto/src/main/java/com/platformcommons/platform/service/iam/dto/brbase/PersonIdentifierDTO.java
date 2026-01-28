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
public class PersonIdentifierDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	// Id of PersonIdentifier
	@NotNull(message = "PersonIdentifier is required")
	@ApiModelProperty(required = true)
	private Integer id;

	@NotNull(message = "PersonIdentifierDTO# identifierType is required")
	@ApiModelProperty(required = true, value = "GREF.PERSON_IDENTIFIER")
	@Valid
	private GlobalRefDataDTO identifierType;

	// IdentifierNumber of PersonIdentifier
	@Deprecated
	private String identifierNumber;

	private String personIdentifierNumber;


	// Person IsAvailable
	private Boolean isAvailable;

	private Boolean isVerified;

	private GlobalRefDataDTO status;

	public GlobalRefDataDTO getStatus() {
		return this.status;
	}

	public void setStatus(GlobalRefDataDTO status) {
		this.status = status;
	}

	public Boolean getIsVerified() {
		return this.isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean available) {
		isAvailable = available;
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
	 * @return the identifierType
	 */
	public GlobalRefDataDTO getIdentifierType() {
		return identifierType;
	}

	/**
	 * @param identifierType
	 *            the identifierType to set
	 */
	public void setIdentifierType(GlobalRefDataDTO identifierType) {
		this.identifierType = identifierType;
	}

	/**
	 * @return the identifierNumber
	 */
	public String getIdentifierNumber() {
		return this.identifierNumber;
	}

	/**
	 * @param identifierNumber
	 *            the identifierNumber to set
	 */
	public void setIdentifierNumber(String identifierNumber) {
		this.identifierNumber = identifierNumber;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonIdentifierDTO other = (PersonIdentifierDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getPersonIdentifierNumber() {
		return personIdentifierNumber;
	}

	public void setPersonIdentifierNumber(String personIdentifierNumber) {
		this.personIdentifierNumber = personIdentifierNumber;
	}

	@Override
	public String toString() {
		return "PersonIdentifierDTO [id = " + id + ",identifierType = " + identifierType +"]";
	}

}