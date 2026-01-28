package com.platformcommons.platform.service.search.application.service.impl;


import com.platformcommons.platform.service.search.application.service.EducationalInstituteService;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.EducationalInstitute;
import com.platformcommons.platform.service.search.domain.repo.EducationalInstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationalInstituteServiceImpl implements EducationalInstituteService {

    @Autowired
    EducationalInstituteRepository educationalInstituteRepository;
    
    @Override
    public EducationalInstitute saveEducationalInstitute(EducationalInstitute data) {
        return educationalInstituteRepository.save(data);
    }

    @Override
    public List<EducationalInstitute> readByName(String name) {
        return educationalInstituteRepository.findByCustomQuery(SearchTermParser.parseSearchTerm(name));
    }
    
    @Override
    public String updateEducationalInstitute(EducationalInstitute data) {
    	if(educationalInstituteRepository.existsById(data.getId())) {
    		educationalInstituteRepository.save(data);
    		return "record updated";
    	}
        return data.getId()+" id not exist";
    }
    
}
