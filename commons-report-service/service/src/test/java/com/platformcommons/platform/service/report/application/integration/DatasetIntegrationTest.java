package com.platformcommons.platform.service.report.application.integration;

import com.platformcommons.platform.context.TenantContext;
import com.platformcommons.platform.context.UserContext;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.report.controller.DataSourceController;
import com.platformcommons.platform.service.report.controller.DatasetController;
import com.platformcommons.platform.service.report.dto.DataSourceDTO;
import com.platformcommons.platform.service.report.dto.DatasetDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class DatasetIntegrationTest {

    @Autowired
    private DatasetController datasetController;
    @Autowired
    private DataSourceController dataSourceController;


    private Long dataSourceId;
    private DatasetDTO datasetDTO;
    private PlatformToken platformToken;

    @BeforeEach
    public void init() {
        DataSourceDTO dataSourceDTO = DataSourceDTO.builder()
                .name(UUID.randomUUID().toString())
                .url("jdbc:mysql://localhost")
                .user("root")
                .password("root")
                .dataSourceDriver("com.mysql.jdbc.Driver")
                .build();
        datasetDTO = DatasetDTO.builder()
                .name(UUID.randomUUID().toString())
                .datasetType("runtime")
                .isCronEnabled(false)
                .queryText("select count(id) from bridgedb.user where u.tenantid = :tenant_id")
                .build();
        setAuthToken();
        Assertions.assertNotNull(dataSourceDTO);
        SecurityContextHolder.getContext().setAuthentication(platformToken);
        dataSourceId = dataSourceController.addDataSource(dataSourceDTO).getBody();
    }

    private void setAuthToken() {
        platformToken = PlatformToken.builder()
                .token(UUID.randomUUID().toString())
                .tenantContext(TenantContext.builder()
                        .tenantId(1L)
                        .build())
                .userContext(UserContext.builder()
                        .userId(1L)
                        .build())
                .build();

    }

    @Test
    public void testAdd() {
        //given
        Assertions.assertNotNull(datasetDTO);

        //when
        ResponseEntity<Long> response = datasetController.addDataset(datasetDTO, dataSourceId,null);

        //then
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    public void testGetById() {
        //given
        Assertions.assertNotNull(datasetDTO);
        ResponseEntity<Long> response = datasetController.addDataset(datasetDTO, dataSourceId,null);

        //when
        ResponseEntity<DatasetDTO> datasetDTOResponseEntity = datasetController
                .getDataSetById(response.getBody());
        //then
        Assertions.assertTrue(datasetDTOResponseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(Objects.requireNonNull(datasetDTOResponseEntity.getBody()).getId(),
                response.getBody());
    }

    @Test
    public void testGetByName() {
        //given
        datasetController.addDataset(datasetDTO, dataSourceId,null);

        //when
        ResponseEntity<DatasetDTO> datasetDTOResponseEntity = datasetController
                .getDataSetByName(datasetDTO.getName());
        //then
        Assertions.assertTrue(datasetDTOResponseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(Objects.requireNonNull(datasetDTOResponseEntity.getBody()).getId(),
                datasetDTOResponseEntity.getBody().getId());
    }

    @Test
    public void testUpdate() {
        //given
        ResponseEntity<Long> response = datasetController.addDataset(datasetDTO, dataSourceId,null);
        ResponseEntity<DatasetDTO> datasetDTOResponseEntity = datasetController
                .getDataSetById(response.getBody());

        //when
        ResponseEntity<Void> responseUpdate = datasetController
                .updateDataset(datasetDTOResponseEntity.getBody());
        //then
        Assertions.assertTrue(responseUpdate.getStatusCode().is2xxSuccessful());
    }

//    @Test
//    public void testAddDataSetTenantPermission(){
//        //given
//        ResponseEntity<Long> response = datasetController.addDataset(datasetDTO, dataSourceId,null);
//
//        //when
//        datasetController.addTenantPermission(response.getBody(),123L);
//
//        //then
//        List<DatasetTenant> responseDSTenant =
//                datasetTenantRepository.findByDataset_id(response.getBody());
//        List<DatasetTenant> filtered = responseDSTenant.stream().filter(datasetTenant
//                -> datasetTenant.getTenant().equals(123L)).collect(Collectors.toList());
//        Assertions.assertFalse(filtered.isEmpty());
//    }
}
