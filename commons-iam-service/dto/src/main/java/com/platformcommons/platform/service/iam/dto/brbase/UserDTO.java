package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonInclude(value = Include.NON_NULL)
@ApiModel("UserDTO")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class UserDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private  String uuid;

	// Id of user
	//@NotNull(message = "USERDTO# id is required")
	//@ApiModelProperty(required = true)
	private BigDecimal userSystemId;

	private String userSystemIdString;


	public BigDecimal getUserSystemId() {
		return userSystemId;
	}

	public void setUserSystemId(BigDecimal userSystemId) {
		this.userSystemId = userSystemId;
	}

	@NotNull(message = "#UserDTO firstName cannot be null")
	@ApiModelProperty(value = "user first name", required = true)
	private String firstName;

	// @NotNull
	// @ApiModelProperty(required=true)
	private String lastName;

	@NotNull(message = "#UserDTO loginName cannot be null")
	@ApiModelProperty(value = "user login name", required = true)
	private String login;

	private String middleName;

	// person of User
	@Valid
	private PersonDTO person;
	
	
	private TenantRefDataDTO creationMode;

	public String getUserSystemIdString() {
		return userSystemIdString;
	}

	public void setUserSystemIdString(String userSystemIdString) {
		this.userSystemIdString = userSystemIdString;
	}


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @return the creationMode
	 */
	public TenantRefDataDTO getCreationMode() {
		return creationMode;
	}

	/**
	 * @param creationMode the creationMode to set
	 */
	public void setCreationMode(TenantRefDataDTO creationMode) {
		this.creationMode = creationMode;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the person
	 */
	public PersonDTO getPerson() {
		return this.person;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(PersonDTO person) {
		this.person = person;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", login=" + login
				+ ", middleName=" + middleName + "]";
	}

	@Builder
	public UserDTO(String aliasId, String notes, Integer id, String uuid, BigDecimal userSystemId, String userSystemIdString,
				   String firstName, String lastName, String login, String middleName, PersonDTO person, TenantRefDataDTO creationMode) {
		super(aliasId, notes);
		this.id = id;
		this.uuid = uuid;
		this.userSystemId = userSystemId;
		this.userSystemIdString = userSystemIdString;
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.middleName = middleName;
		this.person = person;
		this.creationMode = creationMode;
	}
}
