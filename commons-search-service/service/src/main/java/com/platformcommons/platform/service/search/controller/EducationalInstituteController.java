package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.client.EducationalInstituteAPI;
import com.platformcommons.platform.service.search.dto.EducationalInstituteDTO;
import com.platformcommons.platform.service.search.facade.EducationalInstituteFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "EducationalInstitute")
public class EducationalInstituteController implements EducationalInstituteAPI {

    @Autowired
    EducationalInstituteFacade educationalInstituteFacade;
    @Override
    public ResponseEntity<PageDTO<EducationalInstituteDTO>> readEducationalInstitute(String keyword) {
        return new ResponseEntity<>(educationalInstituteFacade.readByName(keyword), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageDTO<EducationalInstituteDTO>> readEducationalInstituteByName(String keyword) {
        return new ResponseEntity<>(educationalInstituteFacade.readByName(keyword), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EducationalInstituteDTO> saveEducationalInstitute(EducationalInstituteDTO body) {
        return new ResponseEntity<>(educationalInstituteFacade.saveEducationalInstitute(body), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateEducationalInstitute(EducationalInstituteDTO body) {
        return new ResponseEntity<>(educationalInstituteFacade.updateEducationalInstitute(body), HttpStatus.OK);
    }
}
