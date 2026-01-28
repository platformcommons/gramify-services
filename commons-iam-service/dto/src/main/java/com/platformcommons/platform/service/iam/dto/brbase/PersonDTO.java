package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

@JsonInclude(value = Include.NON_NULL)
public class PersonDTO extends BaseDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Person#Id is required")
	@ApiModelProperty(required = true)
	private Integer id;

	@NotNull(message = "Person#Person profile is required")
	@Valid
	@ApiModelProperty(required = true)
	private PersonProfileDTO personProfile;

	@Valid
	private List<PersonAddressDTO> personAddresses;

	@Valid
	private List<PersonContactDTO> personContacts;

	@Valid
	private List<PersonIdentifierDTO> personIdentifierList;

	// personEducationList of Person
	@Valid
	private List<PersonEducationDTO> personEducationList;

	// personFamilyList of Person
	@Valid
	private List<PersonFamilyDTO> personFamilyList;

	// personInsuranceList of Person
	@Valid
	private List<PersonInsuranceDTO> personInsuranceList;

	@Valid
	private List<PersonLanguageDTO> personLanguageList;

	private List<PersonSocialMediaDTO> personSocialMedia;

	private List<PersonHobbyDTO> personHobbies;



	@Valid
	private List<PersonProfessionalHistoryDTO> personProfessionalHistoryList;



	public List<PersonHobbyDTO> getPersonHobbies() {
		return personHobbies;
	}

	public void setPersonHobbies(List<PersonHobbyDTO> personHobbies) {
		this.personHobbies = personHobbies;
	}

	public List<PersonLanguageDTO> getPersonLanguageList() {
		return personLanguageList;
	}

	public void setPersonLanguageList(List<PersonLanguageDTO> personLanguageList) {
		this.personLanguageList = personLanguageList;
	}

	public PersonDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PersonProfileDTO getPersonProfile() {
		return personProfile;
	}

	public void setPersonProfile(PersonProfileDTO personProfile) {
		this.personProfile = personProfile;
	}

	public List<PersonAddressDTO> getPersonAddresses() {
		return personAddresses;
	}

	public void setPersonAddresses(List<PersonAddressDTO> personAddresses) {
		this.personAddresses = personAddresses;
	}

	public List<PersonContactDTO> getPersonContacts() {
		return personContacts;
	}

	public void setPersonContacts(List<PersonContactDTO> personContacts) {
		this.personContacts = personContacts;
	}

	/**
	 * @return the personIdentifierList
	 */
	public List<PersonIdentifierDTO> getPersonIdentifierList() {
		return personIdentifierList;
	}

	/**
	 * @param personIdentifierList
	 *            the personIdentifierList to set
	 */
	public void setPersonIdentifierList(
			List<PersonIdentifierDTO> personIdentifierList) {
		this.personIdentifierList = personIdentifierList;
	}

	/**
	 * @return the personEducationList
	 */
	public List<PersonEducationDTO> getPersonEducationList() {
		return this.personEducationList;
	}

	/**
	 * @param personEducationList
	 *            the personEducationList to set
	 */
	public void setPersonEducationList(
			List<PersonEducationDTO> personEducationList) {
		this.personEducationList = personEducationList;
	}

	/**
	 * @return the personFamilyList
	 */
	public List<PersonFamilyDTO> getPersonFamilyList() {
		return this.personFamilyList;
	}

	/**
	 * @param personFamilyList
	 *            the personFamilyList to set
	 */
	public void setPersonFamilyList(List<PersonFamilyDTO> personFamilyList) {
		this.personFamilyList = personFamilyList;
	}

	/**
	 * @return the personInsuranceList
	 */
	public List<PersonInsuranceDTO> getPersonInsuranceList() {
		return this.personInsuranceList;
	}

	/**
	 * @param personInsuranceList
	 *            the personInsuranceList to set
	 */
	public void setPersonInsuranceList(
			List<PersonInsuranceDTO> personInsuranceList) {
		this.personInsuranceList = personInsuranceList;
	}

	public List<PersonSocialMediaDTO> getPersonSocialMedia() {
		return personSocialMedia;
	}

	public void setPersonSocialMedia(List<PersonSocialMediaDTO> personSocialMedia) {
		this.personSocialMedia = personSocialMedia;
	}

	public List<PersonProfessionalHistoryDTO> getPersonProfessionalHistoryList() {
		return personProfessionalHistoryList;
	}

	public void setPersonProfessionalHistoryList(List<PersonProfessionalHistoryDTO> personProfessionalHistoryList) {
		this.personProfessionalHistoryList = personProfessionalHistoryList;
	}

	@Override
	public String toString() {
		return "PersonDTO [id=" + id + ", personProfile=" + personProfile
				+ ", personAddresses=" + personAddresses + ", personContacts="
				+ personContacts +  ", personIdentifiers="
				+ personIdentifierList + "]";
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
		PersonDTO other = (PersonDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Builder
	public PersonDTO(String aliasId, String notes, Integer id, PersonProfileDTO personProfile, List<PersonAddressDTO> personAddresses, List<PersonContactDTO> personContacts, List<PersonIdentifierDTO> personIdentifierList, List<PersonEducationDTO> personEducationList, List<PersonFamilyDTO> personFamilyList, List<PersonInsuranceDTO> personInsuranceList, List<PersonLanguageDTO> personLanguageList, List<PersonSocialMediaDTO> personSocialMedia, List<PersonHobbyDTO> personHobbies, List<PersonProfessionalHistoryDTO> personProfessionalHistoryList) {
		super(aliasId, notes);
		this.id = id;
		this.personProfile = personProfile;
		this.personAddresses = personAddresses;
		this.personContacts = personContacts;
		this.personIdentifierList = personIdentifierList;
		this.personEducationList = personEducationList;
		this.personFamilyList = personFamilyList;
		this.personInsuranceList = personInsuranceList;
		this.personLanguageList = personLanguageList;
		this.personSocialMedia = personSocialMedia;
		this.personHobbies = personHobbies;
		this.personProfessionalHistoryList = personProfessionalHistoryList;
	}
}
