package com.platformcommons.platform.service.iam.dto.brbase;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@JsonInclude(value = Include.NON_NULL)
public class PersonProfessionalHistoryDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// ToDate of PersonProfessionalHistory
	private Date toDate;

	// WorkType of PersonProfessionalHistory
	@NotNull(message = "PersonProfessionalHistoryDTO# workType is required")
	@ApiModelProperty(required = true, value = "GREF class = GREF.WORK_TYPE")
	@Valid
	private GlobalRefDataDTO workType;

	// FromDate of PersonProfessionalHistory
	@NotNull(message = "PersonProfessionalHistoryDTO# fromDate is required")
	@ApiModelProperty(required = true)
	private Date fromDate;

	// Id of PersonProfessionalHistory
	@NotNull(message = "PersonProfessionalHistory id is required")
	@ApiModelProperty(required = true)
	private Integer id;

	// WorkPlace of PersonProfessionalHistory
	private String workPlace;

	// WorkId of PersonProfessionalHistory
	private String workId;

	private Float currentCTC;

	@Valid
	private List<ExtraAttributeDTO> extraAttributeList;
	
	private String designation;
	
	private String jobSector;

	private String employerName;
	
	private Integer durationInMonths;

	// reasonForLeaving of PersonProfessionalHistory
	
	@ApiModelProperty(required = false, value = "GREF class = GREF.REASON_FOR_LEAVING ")
	@Valid
	private GlobalRefDataDTO reasonForLeaving;

	public String getJobSector() {
		return jobSector;
	}

	public void setJobSector(String jobSector) {
		this.jobSector = jobSector;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public Integer getDurationInMonths() {
		return durationInMonths;
	}

	public void setDurationInMonths(Integer durationInMonths) {
		this.durationInMonths = durationInMonths;
	}

	public GlobalRefDataDTO getReasonForLeaving() {
		return reasonForLeaving;
	}

	public void setReasonForLeaving(GlobalRefDataDTO reasonForLeaving) {
		this.reasonForLeaving = reasonForLeaving;
	}

	/**
	 * @return the appCreatedDateTime
	 */
	public Date getAppCreatedDateTime() {
		return this.appCreatedDateTime;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * @param appCreatedDateTime
	 *            the appCreatedDateTime to set
	 */
	public void setAppCreatedDateTime(Date appCreatedDateTime) {
		this.appCreatedDateTime = appCreatedDateTime;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return this.toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the workType
	 */
	public GlobalRefDataDTO getWorkType() {
		return workType;
	}

	/**
	 * @param workType
	 *            the workType to set
	 */
	public void setWorkType(GlobalRefDataDTO workType) {
		this.workType = workType;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return this.fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
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
	 * @return the workPlace
	 */
	public String getWorkPlace() {
		return workPlace;
	}

	/**
	 * @param workPlace
	 *            the workPlace to set
	 */
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	/**
	 * @return the workId
	 */
	public String getWorkId() {
		return workId;
	}

	/**
	 * @param workId
	 *            the workId to set
	 */
	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public Float getCurrentCTC() {
		return currentCTC;
	}

	public void setCurrentCTC(Float currentCTC) {
		this.currentCTC = currentCTC;
	}

	public List<ExtraAttributeDTO> getExtraAttributeList() {
		return extraAttributeList;
	}

	public void setExtraAttributeList(List<ExtraAttributeDTO> extraAttributeList) {
		this.extraAttributeList = extraAttributeList;
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
		PersonProfessionalHistoryDTO other = (PersonProfessionalHistoryDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonProfessionalHistoryDTO [toDate = " + toDate + ",workType = " + workType + ",fromDate = " + fromDate
				+ ",id = " + id + ",workPlace = " + workPlace + ",workId = " + workId + ",currentCTC = " + currentCTC
				+ ",extraAttributeList = " + extraAttributeList + "]";
	}
}