package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * Generated class Will not be over written
 */
@JsonInclude(value = Include.NON_NULL)
public class PersonFamilyDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// AgeAsOnDate of PersonFamily
	private Date ageAsOnDate;

	// DateOfBirth of PersonFamily
	private Date dateOfBirth;

	// MemberName of PersonFamily
	@NotNull(message = "PersonFamilyDTO# memberName is required")
	@ApiModelProperty(required = true)
	private String memberName;

	// Id of PersonFamily
	@NotNull(message = "PersonFamilyDTO# id is required")
	@ApiModelProperty(required = true)
	private Integer id;

	// EducationLevel of PersonFamily
	private String educationLevel;

	// Age of PersonFamily
	private Integer age;

	// Relationship of PersonFamily
	@NotNull(message = "PersonFamilyDTO# relationship is required")
	@Valid
	@ApiModelProperty(required = true, value = "GREF class=GREF.PERSON_RELATIONSHIP_TYPE")
	private GlobalRefDataDTO relationship;

	private ContactDTO contact;

	// Gender of PersonFamily
	@Valid
	@ApiModelProperty(value = "GREF class=GREF.GENDER")
	private GlobalRefDataDTO gender;

	// Occupation of PersonFamily
	private String occupation;

	@Valid
	private List<ExtraAttributeDTO> extraAttributeList;
	
	
	
	public List<ExtraAttributeDTO> getExtraAttributeList() {
		return extraAttributeList;
	}

	public void setExtraAttributeList(List<ExtraAttributeDTO> extraAttributeList) {
		this.extraAttributeList = extraAttributeList;
	}
	
	/**
	 * @return the ageAsOnDate
	 */
	public Date getAgeAsOnDate() {
		return this.ageAsOnDate;
	}

	/**
	 * @param ageAsOnDate
	 *            the ageAsOnDate to set
	 */
	public void setAgeAsOnDate(Date ageAsOnDate) {
		this.ageAsOnDate = ageAsOnDate;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return this.memberName;
	}

	/**
	 * @param memberName
	 *            the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
	 * @return the educationLevel
	 */
	public String getEducationLevel() {
		return this.educationLevel;
	}

	/**
	 * @param educationLevel
	 *            the educationLevel to set
	 */
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return this.age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	public GlobalRefDataDTO getRelationship() {
		return relationship;
	}

	public void setRelationship(GlobalRefDataDTO relationship) {
		this.relationship = relationship;
	}

	/**
	 * @return the contact
	 */
	public ContactDTO getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            the contact to set
	 */
	public void setContact(ContactDTO contact) {
		this.contact = contact;
	}

	/**
	 * @return the gender
	 */
	public GlobalRefDataDTO getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(GlobalRefDataDTO gender) {
		this.gender = gender;
	}

	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * @param occupation
	 *            the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
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
		PersonFamilyDTO other = (PersonFamilyDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonFamilyDTO [ageAsOnDate = " + ageAsOnDate + ",dateOfBirth = " + dateOfBirth + ",memberName = "
				+ memberName + ",id = " + id + ",educationLevel = " + educationLevel + ",age = " + age
				+ ",relationship = " + relationship + ",contact = " + contact + ",gender = " + gender + ",occupation = "
				+ occupation +  "]";
	}
}