package com.platformcommons.platform.service.profile.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.profile.client.PersonAPI;
import com.platformcommons.platform.service.profile.dto.PersonDTO;
import com.platformcommons.platform.service.profile.facade.PersonFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Person")
public class PersonController implements PersonAPI {


    @Autowired
    private PersonFacade personFacade;

    @Override
    public ResponseEntity<Void> deletePerson(Long id, String reason) {
        personFacade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<PersonDTO> getPerson(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(personFacade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<PersonDTO>> getPersonPage(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<Long> postPerson(PersonDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personFacade.save(body));
    }

    @Override
    public ResponseEntity<PersonDTO> putPerson(PersonDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(personFacade.update(body));
    }
}
