package com.platformcommons.platform.service.iam.dto.brbase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSelfRegistrationDTO extends BaseDTO {


	private static final long serialVersionUID = 1L;

	@NotNull(message = "#UserSelfRegistrationDTO id cannot be null")
	private Integer id;

	@NotNull(message = "#UserSelfRegistrationDTO firstName cannot be null")
	private String firstName;

	private String lastName;

	private Date dob;

	private String gender;

	private String appContext;

	@NotNull(message = "#UserSelfRegistrationDTO login cannot be null")
	private String login;

	private Date terminatedAt;

	@Valid
	private List<UserAddressDTO> userAddresses;

	@NotNull(message = "#UserSelfRegistrationDTO contact cannot be null")
	@Valid
	private List<UserContactDTO> userContacts;

	public UserSelfRegistrationResponseDTO getResponseDTO() {
		return responseDTO;
	}

	public void setResponseDTO(UserSelfRegistrationResponseDTO responseDTO) {
		this.responseDTO = responseDTO;
	}

	private UserSelfRegistrationResponseDTO responseDTO;
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
	 * @return the terminatedAt
	 */
	public Date getTerminatedAt() {
		return terminatedAt;
	}

	/**
	 * @param terminatedAt
	 *            the terminatedAt to set
	 */
	public void setTerminatedAt(Date terminatedAt) {
		this.terminatedAt = terminatedAt;
	}

	/**
	 * @return the userContacts
	 */
	public List<UserContactDTO> getUserContacts() {
		return userContacts;
	}

	/**
	 * @param userContacts
	 *            the userContacts to set
	 */
	public void setUserContacts(List<UserContactDTO> userContacts) {
		this.userContacts = userContacts;
	}

	/**
	 *
	 * @return
	 */
	public Date getDob() {
		return dob;
	}



	/**
	 *
	 * @param dob
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAppContext() {
		return appContext;
	}

	public void setAppContext(String appContext) {
		this.appContext = appContext;
	}

	public List<UserAddressDTO> getUserAddresses() {
		return userAddresses;
	}

	public void setUserAddresses(List<UserAddressDTO> userAddresses) {
		this.userAddresses = userAddresses;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "UserSelfRegistrationDTO{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", dob=" + dob +
				", login='" + login + '\'' +
				", terminatedAt=" + terminatedAt +
				", userContacts=" + userContacts +
				'}';
	}


}
