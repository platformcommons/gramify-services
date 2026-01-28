package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.User;
import com.platformcommons.platform.service.search.dto.UserDTO;

public interface UserDTOAssembler {

    UserDTO toDTO(User user);

    User fromDTO(UserDTO dto);

    UserDTO toCustomDTOIgnoringSensitiveInfo(User user);
}
