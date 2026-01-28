package com.platformcommons.platform.service.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.platformcommons.platform.service.app.client.AppConfigAPI;
import com.platformcommons.platform.service.app.dto.AppConfigDTO;
import com.platformcommons.platform.service.app.dto.ConfigDTO;
import com.platformcommons.platform.service.app.dto.DefaultConfigRequestDTO;
import com.platformcommons.platform.service.app.facade.AppConfigFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name="AppConfig")
public class AppConfigController  implements AppConfigAPI {

    @Autowired
    private AppConfigFacade appConfigFacade;


    @Override
    public ResponseEntity<Void> deleteAppConfig(Long id, String reason) {
        appConfigFacade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<AppConfigDTO> getAppConfigs(Long id) {
        return  ResponseEntity.status(HttpStatus.OK).body(appConfigFacade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<AppConfigDTO>> getAppConfigsPage(Integer page, Integer size) {
        PageDTO<AppConfigDTO> results= appConfigFacade.getAllPage(page-1,size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @Override
    public ResponseEntity<Void> postOrUpdateAppConfigIndividually(Long appVersionId,Set<AppConfigDTO> body) {
        appConfigFacade.addOrUpdateAppConfigToAppVersionIndividually(appVersionId,body);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Long> postAppConfig(AppConfigDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appConfigFacade.save(body));
    }

    @Override
    public ResponseEntity<AppConfigDTO> putAppConfig(AppConfigDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appConfigFacade.update(body));
    }


    @ApiOperation(value = "getConfigForAppAppCodeAndAppVersion", nickname = "getAppConfigs", tags={ "AppConfig", })
    @RequestMapping(value = "/api/v1/app-config/app",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<Map<String, ConfigDTO>> getConfigForAppAppCodeAndAppVersion(@RequestParam("appCode")String appCode,
                                                                                      @RequestParam("version")String version,
                                                                                      @RequestParam("languageCode")String languageCode,
                                                                                      @RequestParam("scope") Map<String,String> scope){
        return ResponseEntity.status(HttpStatus.OK).body(appConfigFacade.getConfigByAppCodeAndAppVersionForLanguage(appCode,version, scope,languageCode));
    }

    @ApiOperation(value = "getConfigForAppAppCodeAndAppVersion", nickname = "getAppConfigs", tags={ "AppConfig", })
    @RequestMapping(value = "/api/v1/app-config/app/type",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> getConfigForAppAppCodeAndAppVersionWithConfigType(@RequestParam("appCode")String appCode,
                                                                                      @RequestParam("version")String version,
                                                                                      @RequestParam(value = "configType",required = false)String configType,
                                                                                      @RequestParam("languageCode")String languageCode,
                                                                                      @RequestParam Map<String,String> scope){
        return ResponseEntity.status(HttpStatus.OK).body(appConfigFacade.getConfigForAppAppCodeAndAppVersionWithConfigType
                (appCode,version, scope,languageCode,configType));
    }


    @ApiOperation(value = "getConfigForAppAppCodeAndAppVersion", nickname = "getAppConfigs", tags={ "AppConfig", })
    @RequestMapping(value = "/api/v3/app-config/app/type",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<List<AppConfigDTO>> getConfigForAppAppCodeAndAppVersionWithConfigTypeV3(@RequestParam("appCode")String appCode,
                                                                                                 @RequestParam("version")String version,
                                                                                                 @RequestParam(value = "configType",required = false)String configType,
                                                                                                 @RequestParam Map<String,String> scope){
        return ResponseEntity.status(HttpStatus.OK).body(appConfigFacade.getConfigForAppAppCodeAndAppVersionWithConfigTypeV3
                (appCode,version, scope,configType));
    }

    @ApiOperation(value = "getConfigsForAppAppCodeAndAppVersionWithScopeValue", nickname = "getAppConfigs", tags={ "AppConfig", })
    @RequestMapping(value = "/api/v1/app-config/{appCode}/{appVersion}/tenant/{scopeValue}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<List<AppConfigDTO>> getConfigsForAppAppCodeAndAppVersionWithScopeValue(
                                                                            @PathVariable("appCode") String appCode,
                                                                            @PathVariable("appVersion") String appVersion,
                                                                            @PathVariable("scopeValue") String scopeValue) {
        return ResponseEntity.status(HttpStatus.OK).body(
                appConfigFacade.getAppConfigsForAppAppCodeAndAppVersionWithScopeValue(appCode,appVersion,scopeValue));

    }

    @ApiOperation(value = "saveOrUpdateAllFormJson", nickname = "saveAppConfigs", tags={ "AppConfig", })
    @RequestMapping(value = "/api/v1/app-config/app",
            produces = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<List<AppConfigDTO>> saveOrUpdateAllFormJson(@RequestBody Map<String,Object> configKeyValueMap,
                                                      @RequestParam Long appVersionId,
                                                      @RequestParam String configType,
                                                      @RequestParam String scopeCode,@RequestParam(required = false) String scopeValue){
        appConfigFacade.saveOrUpdateAll(configKeyValueMap,appVersionId,configType,scopeCode,scopeValue);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "saveOrUpdateAppConfigsWithHierarchical", nickname = "saveAppConfigsWithHierarchical", tags={ "AppConfig", })
    @RequestMapping(value = "/api/v2/app-config/app",
            produces = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<Void> saveOrUpdateAllWithNested(@RequestBody com.fasterxml.jackson.databind.JsonNode configKeyValueMap,
                                                  @RequestParam Long appVersionId,
                                                  @RequestParam String configType,
                                                  @RequestParam String scopeCode, @RequestParam(required = false) String scopeValue){
        appConfigFacade.saveOrUpdateAllWithNested(configKeyValueMap.toString()
                ,appVersionId,configType,scopeCode,scopeValue);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "getConfigForAppAppCodeAndAppVersionNestedStructure", nickname = "getAppConfigs", tags={ "AppConfig", })
    @RequestMapping(value = "/api/v2/app-config/app/type",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<com.fasterxml.jackson.databind.JsonNode> getConfigForAppAppCodeAndAppVersionNestedStructure(@RequestParam("appCode")String appCode,
                                                                                          @RequestParam("version")String version,
                                                                                          @RequestParam(value = "configType",required = false)String configType,
                                                                                          @RequestParam("languageCode")String languageCode,
                                                                                          @RequestParam Map<String,String> scope) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(appConfigFacade.
                getConfigForAppAppCodeAndAppVersionNestedStructure
                        (appCode,version, scope,languageCode,configType));
    }

    @Override
    public ResponseEntity<Void> createConfigsFromDefaultAppVersion(@RequestBody DefaultConfigRequestDTO defaultConfigRequestDTO){

        appConfigFacade.createDefaultConfig(defaultConfigRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "uploadAttachment", nickname = "uploadAttachment", notes = "",tags={ "AppConfig", })
    @RequestMapping(value = "/api/v1/app-configs/attachment",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<AttachmentDTO> uploadAttachment(@RequestPart MultipartFile file, @RequestParam(required = false) String entityType){
        return ResponseEntity.status(HttpStatus.OK).body(appConfigFacade.uploadAttachment(file,entityType));
    }
}
