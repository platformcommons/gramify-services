package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.EducationalInstitute;

import java.util.List;

public interface EducationalInstituteService {

	EducationalInstitute saveEducationalInstitute(EducationalInstitute data);

	List<EducationalInstitute> readByName(String name);
	
	String updateEducationalInstitute(EducationalInstitute data);
}
