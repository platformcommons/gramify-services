package com.platformcommons.platform.service.domain.controller;

import com.platformcommons.platform.service.domain.client.FeatureAPI;
import com.platformcommons.platform.service.domain.dto.FeatureDTO;
import com.platformcommons.platform.service.domain.facade.FeatureFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="Feature")
public class FeatureController implements FeatureAPI {

    @Autowired
    private FeatureFacade featureFacade;

    @Override
    public ResponseEntity<Void> deleteFeature(Long id, String reason) {
        featureFacade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<FeatureDTO> getFeature(Long id) {
        return  ResponseEntity.status(HttpStatus.OK).body(featureFacade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<FeatureDTO>> getFeaturePage(Integer page, Integer size) {
        PageDTO<FeatureDTO> result= featureFacade.getAllPage(page,size);
        return ResponseEntity.status(result.hasNext()? HttpStatus.PARTIAL_CONTENT: HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<Long> postFeature(FeatureDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(featureFacade.save(body));
    }

    @Override
    public ResponseEntity<FeatureDTO> putFeature(FeatureDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(featureFacade.update(body));
    }
}
