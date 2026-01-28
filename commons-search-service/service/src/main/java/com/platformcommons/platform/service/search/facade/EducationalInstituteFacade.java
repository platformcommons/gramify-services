package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.domain.EducationalInstitute;
import com.platformcommons.platform.service.search.dto.EducationalInstituteDTO;

import java.util.List;

public interface EducationalInstituteFacade {

	EducationalInstituteDTO saveEducationalInstitute(EducationalInstituteDTO body);

	PageDTO<EducationalInstituteDTO> readByName(String keyword);
	
	String updateEducationalInstitute(EducationalInstituteDTO body);
}
