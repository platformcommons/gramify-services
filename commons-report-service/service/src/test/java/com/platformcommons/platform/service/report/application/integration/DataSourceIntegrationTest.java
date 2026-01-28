package com.platformcommons.platform.service.report.application.integration;

import com.platformcommons.platform.context.TenantContext;
import com.platformcommons.platform.context.UserContext;
import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.report.controller.DataSourceController;
import com.platformcommons.platform.service.report.dto.DataSourceDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;

import java.util.Objects;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class DataSourceIntegrationTest {

    @Autowired
    private DataSourceController dataSourceController;

    private static DataSourceDTO dataSourceDTO;
    private static PlatformToken platformToken;

    @BeforeAll
    public static void init() {
        dataSourceDTO = DataSourceDTO.builder()
                .name("localhost")
                .url("jdbc:mysql://localhost")
                .user("root")
                .password("root")
                .dataSourceDriver("com.mysql.jdbc.Driver")
                .build();

        setAuthToken();
    }

    private static void setAuthToken() {
        platformToken = PlatformToken.builder()
                .token(UUID.randomUUID().toString())
                .tenantContext(TenantContext.builder()
                        .tenantId(1L)
                        .build())
                .userContext(UserContext.builder()
                        .userId(1L)
                        .build())
                .build();
        SecurityContextHolder.getContext().setAuthentication(platformToken);
    }

    @Test
    public void testAdd() {
        //given
        Assertions.assertNotNull(dataSourceDTO);


        //when
        ResponseEntity<Long> response = dataSourceController.addDataSource(dataSourceDTO);

        //then
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    public void testGetById() {
        //given
        Assertions.assertNotNull(dataSourceDTO);
        ResponseEntity<Long> response = dataSourceController.addDataSource(dataSourceDTO);

        //when
        ResponseEntity<DataSourceDTO> dataSourceDTOResponseEntity = dataSourceController
                .getDataSourceById(response.getBody());
        //then
        Assertions.assertTrue(dataSourceDTOResponseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(Objects.requireNonNull(dataSourceDTOResponseEntity.getBody()).getId(),
                response.getBody());
    }

    @Test
    public void testUpdate() {
        //given
        Assertions.assertNotNull(dataSourceDTO);
        ResponseEntity<Long> response = dataSourceController.addDataSource(dataSourceDTO);
        ResponseEntity<DataSourceDTO> dataSourceDTOResponseEntity = dataSourceController
                .getDataSourceById(response.getBody());

        //when
        ResponseEntity<Void> responseUpdate = dataSourceController
                .updateDataSource(dataSourceDTOResponseEntity.getBody());
        //then
        Assertions.assertTrue(responseUpdate.getStatusCode().is2xxSuccessful());
    }
}
