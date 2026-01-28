package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

@JsonInclude(value = Include.NON_NULL)
public class PersonProfileDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "PersonProfile#Id is required")
	@ApiModelProperty(required = true)
	private Integer id;

	@NotNull(message = "PersonProfile#First name is required")
	@Size(min = 1, message = "PersonProfile#Name must be more than 1 character")
	@ApiModelProperty(required = true)
	private String firstName;

	private String middleName;

	// @NotNull(message="PersonProfile#Last name is required")
	// @ApiModelProperty(required=true)
	private String lastName;

	private Date dateOfBirth;

	// @NotNull(message="PersonProfile#gender is required")
	@Valid
	@ApiModelProperty(value = "classCode=GREF.GENDER", required = false)
	private GlobalRefDataDTO gender;

	// @NotNull(message="PersonProfile#age is required")
	// @ApiModelProperty(required=true)
	private Integer age;

	private Double gratuity;

	private Double providentFund;

	private Double incomeTax;

	private Date ageAsOnDate;

	@Valid
	@ApiModelProperty(value = "classCode=GREF.BLOOD_GROUP")
	private GlobalRefDataDTO bloodGroup;

	private String disabilityDetails;

	@Valid
	@ApiModelProperty(value = "classCode=GREF.DISABILITY_STATUS")
	private GlobalRefDataDTO disabilityStatus;

	@Valid
	@ApiModelProperty(value = "classCode=GREF.MARITAL_STATUS")
	private GlobalRefDataDTO maritalStatus;

	private Integer noOfChildren;

	@Valid
	@ApiModelProperty(value = "classCode=GREF.SALUTATION")
	private GlobalRefDataDTO salutation;

	@Valid
	@ApiModelProperty(value = "classCode=GREF.LIFE_STATUS")
	private GlobalRefDataDTO lifeStatus;

	private Double shoeLengthInMM;

	private Double shoeWidthInMM;

	private String caste;

	@Valid
	private List<MLTextDTO> firstNameML;

	@Valid
	private List<MLTextDTO> middleNameML;

	@Valid
	private List<MLTextDTO> lastNameML;

	private String religion;

	// Occupation of PersonFamily
	private String occupation;


	@Valid
	private GlobalRefDataDTO category;

	private String motherTongue;

	@ApiModelProperty(required = false)
	@Valid
	private UOMValueDTO totalWorkExperience;


	private TenantRefDataDTO source;
	
	/*
	 * private String subSource;

	*/
	
	
	/**
	 * @return the source
	 */
	public TenantRefDataDTO getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(TenantRefDataDTO source) {
		this.source = source;
	}

	public Double getGratuity() {
		return gratuity;
	}

	public void setGratuity(Double gratuity) {
		this.gratuity = gratuity;
	}

	public Double getProvidentFund() {
		return providentFund;
	}

	public void setProvidentFund(Double providentFund) {
		this.providentFund = providentFund;
	}

	public Double getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(Double incomeTax) {
		this.incomeTax = incomeTax;
	}



	public UOMValueDTO getTotalWorkExperience() {
		return totalWorkExperience;
	}

	public void setTotalWorkExperience(UOMValueDTO totalWorkExperience) {
		this.totalWorkExperience = totalWorkExperience;
	}


	public PersonProfileDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public GlobalRefDataDTO getGender() {
		return gender;
	}

	public void setGender(GlobalRefDataDTO gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public GlobalRefDataDTO getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(GlobalRefDataDTO bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getDisabilityDetails() {
		return disabilityDetails;
	}

	public void setDisabilityDetails(String disabilityDetails) {
		this.disabilityDetails = disabilityDetails;
	}

	public GlobalRefDataDTO getDisabilityStatus() {
		return disabilityStatus;
	}

	public void setDisabilityStatus(GlobalRefDataDTO disabilityStatus) {
		this.disabilityStatus = disabilityStatus;
	}

	public GlobalRefDataDTO getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(GlobalRefDataDTO maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Integer getNoOfChildren() {
		return noOfChildren;
	}

	public void setNoOfChildren(Integer noOfChildren) {
		this.noOfChildren = noOfChildren;
	}

	public GlobalRefDataDTO getSalutation() {
		return salutation;
	}

	public void setSalutation(GlobalRefDataDTO salutation) {
		this.salutation = salutation;
	}

	public List<MLTextDTO> getFirstNameML() {
		return firstNameML;
	}

	public void setFirstNameML(List<MLTextDTO> firstNameML) {
		this.firstNameML = firstNameML;
	}

	public List<MLTextDTO> getMiddleNameML() {
		return middleNameML;
	}

	public void setMiddleNameML(List<MLTextDTO> middleNameML) {
		this.middleNameML = middleNameML;
	}

	public List<MLTextDTO> getLastNameML() {
		return lastNameML;
	}

	public void setLastNameML(List<MLTextDTO> lastNameML) {
		this.lastNameML = lastNameML;
	}

	/**
	 * @return the lifeStatus
	 */
	public GlobalRefDataDTO getLifeStatus() {
		return lifeStatus;
	}

	/**
	 * @param lifeStatus
	 *            the lifeStatus to set
	 */
	public void setLifeStatus(GlobalRefDataDTO lifeStatus) {
		this.lifeStatus = lifeStatus;
	}

	/**
	 * @return the shoeLengthInMM
	 */
	public Double getShoeLengthInMM() {
		return shoeLengthInMM;
	}

	/**
	 * @param shoeLengthInMM
	 *            the shoeLengthInMM to set
	 */
	public void setShoeLengthInMM(Double shoeLengthInMM) {
		this.shoeLengthInMM = shoeLengthInMM;
	}

	/**
	 * @return the shoeWidthInMM
	 */
	public Double getShoeWidthInMM() {
		return shoeWidthInMM;
	}

	/**
	 * @param shoeWidthInMM
	 *            the shoeWidthInMM to set
	 */
	public void setShoeWidthInMM(Double shoeWidthInMM) {
		this.shoeWidthInMM = shoeWidthInMM;
	}

	/**
	 * @return the caste
	 */
	public String getCaste() {
		return caste;
	}

	/**
	 * @param caste
	 *            the caste to set
	 */
	public void setCaste(String caste) {
		this.caste = caste;
	}

	/**
	 * @return the ageAsOnDate
	 */
	public Date getAgeAsOnDate() {
		return ageAsOnDate;
	}

	/**
	 * @param ageAsOnDate
	 *            the ageAsOnDate to set
	 */
	public void setAgeAsOnDate(Date ageAsOnDate) {
		this.ageAsOnDate = ageAsOnDate;
	}

	/**
	 * @return the religion
	 */
	public String getReligion() {
		return religion;
	}

	/**
	 * @param religion
	 *            the religion to set
	 */
	public void setReligion(String religion) {
		this.religion = religion;
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



	/**
	 * @return the category
	 */
	public GlobalRefDataDTO getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(GlobalRefDataDTO category) {
		this.category = category;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
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
		PersonProfileDTO other = (PersonProfileDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonProfileDTO [id = " + id + ",firstName = " + firstName + ",middleName = " + middleName
				+ ",lastName = " + lastName + ",dateOfBirth = " + dateOfBirth + ",gender = " + gender + ",age = " + age
				+ ",ageAsOnDate = " + ageAsOnDate + ",bloodGroup = " + bloodGroup + ",disabilityDetails = "
				+ disabilityDetails + ",disabilityStatus = " + disabilityStatus + ",maritalStatus = " + maritalStatus
				+ ",noOfChildren = " + noOfChildren + ",salutation = " + salutation + ",lifeStatus = " + lifeStatus
				+ ",shoeLengthInMM = " + shoeLengthInMM + ",shoeWidthInMM = " + shoeWidthInMM + ",caste = " + caste
				+ ",firstNameML = " + firstNameML + ",middleNameML = " + middleNameML + ",lastNameML = " + lastNameML
				+ ",religion = " + religion + ",occupation = " + occupation
				+ ",category = " + category + ",motherTongue = " + motherTongue + "]";
	}

	@Builder
	public PersonProfileDTO(String aliasId, String notes, Integer id, String firstName, String middleName, String lastName, Date dateOfBirth, GlobalRefDataDTO gender, Integer age, Double gratuity, Double providentFund, Double incomeTax, Date ageAsOnDate, GlobalRefDataDTO bloodGroup, String disabilityDetails, GlobalRefDataDTO disabilityStatus, GlobalRefDataDTO maritalStatus, Integer noOfChildren, GlobalRefDataDTO salutation, GlobalRefDataDTO lifeStatus, Double shoeLengthInMM, Double shoeWidthInMM, String caste, List<MLTextDTO> firstNameML, List<MLTextDTO> middleNameML, List<MLTextDTO> lastNameML, String religion, String occupation, GlobalRefDataDTO category, String motherTongue, UOMValueDTO totalWorkExperience, TenantRefDataDTO source) {
		super(aliasId, notes);
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.age = age;
		this.gratuity = gratuity;
		this.providentFund = providentFund;
		this.incomeTax = incomeTax;
		this.ageAsOnDate = ageAsOnDate;
		this.bloodGroup = bloodGroup;
		this.disabilityDetails = disabilityDetails;
		this.disabilityStatus = disabilityStatus;
		this.maritalStatus = maritalStatus;
		this.noOfChildren = noOfChildren;
		this.salutation = salutation;
		this.lifeStatus = lifeStatus;
		this.shoeLengthInMM = shoeLengthInMM;
		this.shoeWidthInMM = shoeWidthInMM;
		this.caste = caste;
		this.firstNameML = firstNameML;
		this.middleNameML = middleNameML;
		this.lastNameML = lastNameML;
		this.religion = religion;
		this.occupation = occupation;
		this.category = category;
		this.motherTongue = motherTongue;
		this.totalWorkExperience = totalWorkExperience;
		this.source = source;
	}
}
