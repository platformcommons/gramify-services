package com.platformcommons.platform.service.iam.dto.brbase;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Generated class Will not be over written
 */
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class PersonHobbyDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// Hobby of PersonHobby
	private String hobby;

	// Id of PersonHobby
	@NotNull(message = "PersonHobbyDTO# id is required")
	@ApiModelProperty(required = true)
	private Integer id;

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
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
		PersonHobbyDTO other = (PersonHobbyDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Builder
	public PersonHobbyDTO(String hobby, Integer id) {
		this.hobby = hobby;
		this.id = id;
	}
}