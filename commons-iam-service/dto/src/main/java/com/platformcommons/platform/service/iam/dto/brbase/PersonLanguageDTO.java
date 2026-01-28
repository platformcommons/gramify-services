package com.platformcommons.platform.service.iam.dto.brbase;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor
public class PersonLanguageDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Id of PersonLanguageDTO
	@NotNull(message = "PersonLanguageDTO id is required")
	@ApiModelProperty(required = true)
	private Integer id;
	
	@Valid
	@NotNull(message = "PersonLanguageDTO# Language cannot be null")
	@ApiModelProperty(required = true)
	private LanguageDTO language;

	private GlobalRefDataDTO canSpeak;
	
	private GlobalRefDataDTO canWrite;
	
	
	
	private GlobalRefDataDTO canRead;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LanguageDTO getLanguage() {
		return language;
	}

	public void setLanguage(LanguageDTO language) {
		this.language = language;
	}

	public GlobalRefDataDTO getCanSpeak() {
		return canSpeak;
	}

	public void setCanSpeak(GlobalRefDataDTO canSpeak) {
		this.canSpeak = canSpeak;
	}

	public GlobalRefDataDTO getCanWrite() {
		return canWrite;
	}

	public void setCanWrite(GlobalRefDataDTO canWrite) {
		this.canWrite = canWrite;
	}

	public GlobalRefDataDTO getCanRead() {
		return canRead;
	}

	public void setCanRead(GlobalRefDataDTO canRead) {
		this.canRead = canRead;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((canRead == null) ? 0 : canRead.hashCode());
		result = prime * result
				+ ((canSpeak == null) ? 0 : canSpeak.hashCode());
		result = prime * result
				+ ((canWrite == null) ? 0 : canWrite.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
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
		PersonLanguageDTO other = (PersonLanguageDTO) obj;
		if (canRead == null) {
			if (other.canRead != null)
				return false;
		} else if (!canRead.equals(other.canRead))
			return false;
		if (canSpeak == null) {
			if (other.canSpeak != null)
				return false;
		} else if (!canSpeak.equals(other.canSpeak))
			return false;
		if (canWrite == null) {
			if (other.canWrite != null)
				return false;
		} else if (!canWrite.equals(other.canWrite))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonLanguageDTO [id=" + id + ", language=" + language
				+ ", canSpeak=" + canSpeak + ", canWrite=" + canWrite
				+ ", canRead=" + canRead + "]";
	}

	@Builder
	public PersonLanguageDTO(String aliasId, String notes, Integer id, LanguageDTO language, GlobalRefDataDTO canSpeak,
							 GlobalRefDataDTO canWrite, GlobalRefDataDTO canRead) {
		super(aliasId, notes);
		this.id = id;
		this.language = language;
		this.canSpeak = canSpeak;
		this.canWrite = canWrite;
		this.canRead = canRead;
	}
}
