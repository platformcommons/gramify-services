package com.platformcommons.platform.service.iam.facade.client;

import com.platformcommons.platform.service.iam.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@FeignClient(name = "commons-report-service${commons.service.commons-report-service.context-path:/commons-report-service}", contextId = "DatasetClient")
public interface DatasetClient {


    @GetMapping("/api/v3/datasets/name/{Query}/execute")
    List<Map<String,Object>> executeQueryV3(@PathVariable("Query") String query,
                                            @RequestParam("params") String params,
                                            @RequestParam("page") Integer page,
                                            @RequestParam("size") Integer size,
                                            @RequestHeader("X-SESSIONID") String sessionId
    );
    @GetMapping("/api/v3/datasets/name/{Query}/execute")
    List<Map<String,Object>> executeQueryV3AppKey(@PathVariable("Query") String query,
                                                 @RequestParam("params") String params,
                                                 @RequestParam("page") Integer page,
                                                 @RequestParam("size") Integer size,
                                                 @RequestHeader("Authorization") String appkey
    );

    @RequestMapping(
            value = "/api/v1/datasets/name/VMS_USER_DETAILS_IN_BULK/execute",
            method = RequestMethod.GET)
    ResponseEntity<List<UserDetailsDTO>> getUserDetailsInBulk(@RequestHeader("X-SESSIONID") String sessionId,
                                                              @RequestParam(value = "params") String params);

    @RequestMapping(
            value = "/api/v1/datasets/name/VMS_GET_REF_DATA_CODE_FROM_LABEL/execute",
            method = RequestMethod.GET)
    ResponseEntity<List<RefDataCodeAndLabelDTO>> getRefDataCodeFromLabel(@RequestHeader("X-SESSIONID") String sessionId,
                                                                                         @RequestParam(value = "params") String params);

    @RequestMapping(
            value = "/api/v1/datasets/name/VMS_GET_PERSON_AND_PERSON_PROFILE_IDS/execute",
            method = RequestMethod.GET)
    ResponseEntity<List<UserPersonProfileDTO>> getPersonAndPersonProfileIds(@RequestHeader("X-SESSIONID") String sessionId,
                                                                            @RequestParam(value = "params") String params);

    @RequestMapping(
            value = "/api/v1/datasets/name/VMS_GET_USER_ROLE_BY_ROLE_CODE/execute",
            method = RequestMethod.GET)
    ResponseEntity<List<UserRoleDTO>> getUserRoleByRoleCode(@RequestHeader("X-SESSIONID") String sessionId,
                                                            @RequestParam(value = "params") String params);


    @GetMapping("/api/v2/datasets/name/CAMPUS_CLOUD_USER_LOGIN_TENANT/execute")
    ResponseEntity<List<TenantLoginVO>> getUserLoginV2AppKey(@RequestParam("params") String params,
                                             @RequestParam("page") Integer page,
                                             @RequestParam("size") Integer size,
                                             @RequestHeader("Authorization") String appkey);

    @GetMapping("/api/v2/datasets/name/VMS_COMPUTE_TENANT_FOR_USER_LOGIN/execute")
    ResponseEntity<List<UserTenantCheckDTO>> computeTenantForUserLogin(@RequestParam("params") String params,
                                                                    @RequestParam("page") Integer page,
                                                                    @RequestParam("size") Integer size,
                                                                    @RequestHeader("Authorization") String appkey);

    @RequestMapping(
            value = "/api/v2/datasets/name/VMS_CHECK_FOR_USER_ACTIVATION/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<Map<String, Object>>> checkForUserActivation(@RequestHeader("Authorization") String apikey,
                                                                          @RequestParam(value = "params") String params,
                                                                          @RequestParam("page") Integer page,
                                                                          @RequestParam("size") Integer size);

    @RequestMapping(
            value = "/api/v2/datasets/name/VMS_GET_TENANT_ID_BY_USER_LOGIN/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<TenantIdDTO>> getTenantIdsByUserLogin(@RequestHeader("Authorization") String apikey,
                                                              @RequestParam(value = "params") String params,
                                                              @RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size);

    @RequestMapping(
            value = "/api/v2/datasets/name/VMS_GET_ADMIN_NAME_EMAIL_BY_TENANT_ID/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<Map<String,String>>> getTenantAdminNameAndEmail(@RequestHeader("Authorization") String apikey,
                                                              @RequestParam(value = "params") String params,
                                                              @RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size);

    @RequestMapping(
            value = "/api/v2/datasets/name/VMS_CHECK_IF_USER_EXIST_IN_TENANT/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<Map<String,Object>>> checkIfUserExistsInTenant(@RequestHeader("Authorization") String apikey,
                                                                       @RequestParam(value = "params") String params,
                                                                       @RequestParam("page") Integer page,
                                                                       @RequestParam("size") Integer size);
}
