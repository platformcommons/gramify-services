package com.platformcommons.platform.service.domain.controller;

import com.platformcommons.platform.service.domain.client.BenefitAPI;
import com.platformcommons.platform.service.domain.dto.BenefitDTO;
import com.platformcommons.platform.service.domain.facade.BenefitFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="Benefit")
public class BenefitController implements BenefitAPI {

    @Autowired
    private BenefitFacade benefitFacade;

    @Override
    public ResponseEntity<Void> deleteBenefit(Long id, String reason) {
        benefitFacade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<BenefitDTO> getBenefit(Long id) {
        return  ResponseEntity.status(HttpStatus.OK).body(benefitFacade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<BenefitDTO>> getBenefitPage(Integer page, Integer size) {
        PageDTO<BenefitDTO> result= benefitFacade.getAllPage(page,size);
        return ResponseEntity.status(result.hasNext()? HttpStatus.PARTIAL_CONTENT: HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<Long> postBenefit(BenefitDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(benefitFacade.save(body));
    }

    @Override
    public ResponseEntity<BenefitDTO> putBenefit(BenefitDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(benefitFacade.update(body));
    }
}
