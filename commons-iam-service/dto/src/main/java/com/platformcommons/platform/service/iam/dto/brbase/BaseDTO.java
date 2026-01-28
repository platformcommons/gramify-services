package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.NoArgsConstructor;

@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor
public class BaseDTO implements Serializable {

	public String aliasId;
	
	public String notes;
	
	public Boolean isActive;
	
	public String inActiveReason;
	
	public Date appCreatedDateTime;
	
	public Date appUpdatedDateTime;
	
	public Date createdDateTime;
	
	public Date updatedDateTime;
	
	public String iconpic;
	
	public String getIconpic() {
		return iconpic;
	}

	public void setIconpic(String iconpic) {
		this.iconpic = iconpic;
	}

	public String getAliasId() {
		return aliasId;
	}

	public void setAliasId(String aliasId) {
		this.aliasId = aliasId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	

	/**
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the inAactiveReason
	 */
	public String getInActiveReason() {
		return inActiveReason;
	}

	/**
	 * @param inAactiveReason the inAactiveReason to set
	 */
	public void setInActiveReason(String inActiveReason) {
		this.inActiveReason = inActiveReason;
	}

	public Date getAppCreatedDateTime() {
		return appCreatedDateTime;
	}

	public void setAppCreatedDateTime(Date appCreatedDateTime) {
		this.appCreatedDateTime = appCreatedDateTime;
	}

	public Date getAppUpdatedDateTime() {
		return appUpdatedDateTime;
	}

	public void setAppUpdatedDateTime(Date appUpdatedDateTime) {
		this.appUpdatedDateTime = appUpdatedDateTime;
	}
	
	/**
	 * @return the createdDateTime
	 */
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * @param createdDateTime the createdDateTime to set
	 */
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
	/**
	 * @return the updatedDateTime
	 */
	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	/**
	 * @param updatedDateTime the updatedDateTime to set
	 */
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public BaseDTO(String aliasId, String notes) {
		this.aliasId = aliasId;
		this.notes = notes;
	}
}
