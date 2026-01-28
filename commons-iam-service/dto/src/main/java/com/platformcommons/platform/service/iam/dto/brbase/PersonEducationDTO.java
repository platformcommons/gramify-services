package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Generated class Will not be over written
 */
@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor
public class PersonEducationDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// YearOfPassing of PersonEducation
	private Date yearOfPassing;

	private Boolean isPrimary;


	// Education of PersonEducation
	//@NotNull(message = "PersonEducationDTO# education is required")
	//@ApiModelProperty(required = true)
	private String education;

	// Institution of PersonEducation
	private String institution;

	// Percentage of PersonEducation
	@Max(100)
	private Double percentage;

	// Id of PersonEducation
	@NotNull(message = "PersonEducationDTO# id is required")
	@ApiModelProperty(required = true)
	private Integer id;

	// DropoutStatus of PersonEducation
	private Boolean dropoutStatus;

	// DropoutReason of PersonEducation
	private String dropoutReason;

	@Valid
	private List<ExtraAttributeDTO> extraAttributeList;
	
	private GlobalRefDataDTO stream ;
	
	private GlobalRefDataDTO subject ;
	
	private GlobalRefDataDTO educationCategory;

	public GlobalRefDataDTO getEducationCategory() {
		return educationCategory;
	}

	public void setEducationCategory(GlobalRefDataDTO educationCategory) {
		this.educationCategory = educationCategory;
	}


	public GlobalRefDataDTO getStream() {
		return stream;
	}

	public void setStream(GlobalRefDataDTO stream) {
		this.stream = stream;
	}

	public GlobalRefDataDTO getSubject() {
		return subject;
	}

	public void setSubject(GlobalRefDataDTO subject) {
		this.subject = subject;
	}

	public Boolean getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Boolean primary) {
		isPrimary = primary;
	}


	/**
	 * @return the yearOfPassing
	 */
	public Date getYearOfPassing() {
		return this.yearOfPassing;
	}

	/**
	 * @param yearOfPassing
	 *            the yearOfPassing to set
	 */
	public void setYearOfPassing(Date yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}

	/**
	 * @return the education
	 */
	public String getEducation() {
		return this.education;
	}

	/**
	 * @param education
	 *            the education to set
	 */
	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * @return the institution
	 */
	public String getInstitution() {
		return this.institution;
	}

	/**
	 * @param institution
	 *            the institution to set
	 */
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/**
	 * @return the percentage
	 */
	public Double getPercentage() {
		return this.percentage;
	}

	/**
	 * @param percentage
	 *            the percentage to set
	 */
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
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
	 * @return the dropoutStatus
	 */
	public Boolean getDropoutStatus() {
		return this.dropoutStatus;
	}

	/**
	 * @param dropoutStatus
	 *            the dropoutStatus to set
	 */
	public void setDropoutStatus(Boolean dropoutStatus) {
		this.dropoutStatus = dropoutStatus;
	}

	/**
	 * @return the dropoutReason
	 */
	public String getDropoutReason() {
		return this.dropoutReason;
	}

	/**
	 * @param dropoutReason
	 *            the dropoutReason to set
	 */
	public void setDropoutReason(String dropoutReason) {
		this.dropoutReason = dropoutReason;
	}

	public List<ExtraAttributeDTO> getExtraAttributeList() {
		return extraAttributeList;
	}

	public void setExtraAttributeList(List<ExtraAttributeDTO> extraAttributeList) {
		this.extraAttributeList = extraAttributeList;
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
		PersonEducationDTO other = (PersonEducationDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonEducationDTO [yearOfPassing=" + yearOfPassing + ", education=" + education + ", institution="
				+ institution + ", percentage=" + percentage + ", id=" + id + ", dropoutStatus=" + dropoutStatus
				+ ", dropoutReason=" + dropoutReason + ", extraAttributeList=" + extraAttributeList + "]";
	}

	@Builder
	public PersonEducationDTO(String aliasId, String notes, Date yearOfPassing, Boolean isPrimary, String education, String institution, Double percentage, Integer id, Boolean dropoutStatus, String dropoutReason, List<ExtraAttributeDTO> extraAttributeList, GlobalRefDataDTO stream, GlobalRefDataDTO subject, GlobalRefDataDTO educationCategory) {
		super(aliasId, notes);
		this.yearOfPassing = yearOfPassing;
		this.isPrimary = isPrimary;
		this.education = education;
		this.institution = institution;
		this.percentage = percentage;
		this.id = id;
		this.dropoutStatus = dropoutStatus;
		this.dropoutReason = dropoutReason;
		this.extraAttributeList = extraAttributeList;
		this.stream = stream;
		this.subject = subject;
		this.educationCategory = educationCategory;
	}
}