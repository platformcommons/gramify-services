package com.platformcommons.platform.service.person.dto.ptld;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter  @Setter
public class UserWrapperDTO {


	String confirmPassword;
	String functionName;
	String password;
	List<UserContactDTO> userContactDTOList;
	List<Long> userAddressDTOList=new ArrayList<>();
	UserDTO userDTO;
	String userRoleCode;

	@Builder
	public UserWrapperDTO(String confirmPassword, String functionName, String password, List<UserContactDTO> userContactDTOList, UserDTO userDTO, String userRoleCode) {
		this.confirmPassword = confirmPassword;
		this.functionName = functionName;
		this.password = password;
		this.userContactDTOList = userContactDTOList;
		this.userDTO = userDTO;
		this.userRoleCode = userRoleCode;
	}
}