package com.platformcommons.platform.service.search.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.changemaker.dto.CommunityApplicantDTO;
import com.platformcommons.platform.service.search.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(name = "commons-report-service${commons.service.commons-report-service.context-path:/commons-report-service}",
    contextId = "DatasetClient")
public interface CommonsReportClient {

    @RequestMapping(
            value = "/api/v2/datasets/name/VMS_GET_LABEL_FROM_GLOBAL_REF_DATA_CODES/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<RefDataCodeAndLabelDTO>> getLabelFromGlobalRefDataCodes(@RequestHeader("Authorization") String apikey,
                                                                                @RequestParam(value = "params", required = true) String params,
                                                                                @RequestParam("page") Integer page,
                                                                                @RequestParam("size") Integer size);

    @RequestMapping(
            value = "/api/v2/datasets/name/VMS_GET_LABEL_FROM_CHANGEMAKER_REF_DATA_CODES/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<RefDataCodeAndLabelDTO>> getLabelFromChangemakerRefDataCodes(@RequestHeader("Authorization") String apikey,
                                                                                      @RequestParam(value = "params", required = true) String params,
                                                                                      @RequestParam("page") Integer page,
                                                                                      @RequestParam("size") Integer size);


    @RequestMapping(
            value = "/api/v2/datasets/name/VMS_GET_ROLECODES_BY_USER_ID/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<Map<String,String>>> getRoleCodesByUserId(@RequestHeader("Authorization") String apikey,
                                                                             @RequestParam(value = "params", required = true) String params,
                                                                             @RequestParam("page") Integer page,
                                                                             @RequestParam("size") Integer size);

    @RequestMapping(
            value = "/api/v2/datasets/name/VMS_USER_DETAILS_FOR_OPPORTUNITY_RECOMMENDATION/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<Map<String, String>>> getUserBasicDetailsByUserId(@RequestHeader("Authorization") String apikey,
                                                                          @RequestParam(value = "params") String params,
                                                                          @RequestParam("page") Integer page,
                                                                          @RequestParam("size") Integer size);

    @RequestMapping(
            value = "/api/v2/datasets/name/VMS_GET_WORKFORCE_DETAILS_BY_USER_ID/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<Map<String,String>>> getWorkForceDetailsByUserId(@RequestHeader("Authorization") String apikey,
                                                                  @RequestParam(value = "params", required = true) String params,
                                                                  @RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size);

    @RequestMapping(
            value = "/api/v2/datasets/name/VMS_GET_USER_IDS_BY_HOBBY_PROGRAM_CODES/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<UserIdInfoDTO>> getUserIdsByUserProgramCodes(@RequestHeader("Authorization") String apikey,
                                                                          @RequestParam(value = "params", required = true) String params,
                                                                          @RequestParam("page") Integer page,
                                                                          @RequestParam("size") Integer size);

    @RequestMapping(
            value = "/api/v1/datasets/name/VMS_USER_EXISTS_BY_ROLE_CODE_SUFFIX_GLOBALLY/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<Map<String,String>>> checkIfUserHasGivenRoleSuffixGlobally(@RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId,
                                                                     @RequestParam(value = "params", required = true) String params);

    @RequestMapping(
            value = "/api/v1/datasets/name/VMS_TAGGED_IN_COMMUNITY_FOR_LOGGED_IN_USER/execute",
            method = RequestMethod.GET)
    ResponseEntity<List<CommunityApplicantDTO>> getAllTaggedCommunitiesForLoggedInUser(@RequestHeader("X-SESSIONID") String sessionId);


    @RequestMapping(
            value = "/api/v2/datasets/name/VMS_GET_GLOBAL_REF_DATA_FROM_CODE/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    ResponseEntity<List<GlobalRefDataDTO>> getGlobalRefDataByCodeInBulk(@RequestHeader("Authorization") String apikey,
                                                                               @RequestParam(value = "params", required = true) String params,
                                                                               @RequestParam("page") Integer page,
                                                                               @RequestParam("size") Integer size);

    @RequestMapping(
            value = "/api/v2/datasets/name/GPV_PROUDUCT_SUMMARY_FOR_CHANNEL/execute",
            method = RequestMethod.GET)
    ResponseEntity<Set<GPVSummaryDTO>> getGpvProductSummary(@RequestHeader("Authorization") String appKey,
                                                                       @RequestParam(value = "params", required = false) String params,
                                                                       @RequestParam(value = "page") Integer page,
                                                                       @RequestParam(value = "size") Integer size);

    @RequestMapping(
            value = "/api/v2/datasets/name/GET_TMA_CHANNEL_PRODUCT_SKU_PUBLISH_STATUS/execute",
            method = RequestMethod.GET)
    ResponseEntity<Set<TmaChannelProductStatusDTO>> getTmaChannelProductSkuStatus(@RequestHeader("Authorization") String appKey,
                                                            @RequestParam(value = "params", required = false) String params,
                                                            @RequestParam(value = "page") Integer page,
                                                            @RequestParam(value = "size") Integer size);
}
