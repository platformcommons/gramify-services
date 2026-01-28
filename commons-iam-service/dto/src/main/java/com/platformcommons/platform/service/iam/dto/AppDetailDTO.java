package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppDetailDTO {

    @NotNull
    private Long id;

    private String appCode;

    private String appKey;

    private String sessionId;

    private String supportUserLogin;

    private String supportUserPassword;

    private String tenantLogin;

    private String type;

    private List<String> openApis;

    @Builder
    public AppDetailDTO(Long id, String appCode, String appKey, String supportUserLogin, String supportUserPassword,
                        String tenantLogin, String type, List<String> openApis,String sessionId) {
        this.id = id;
        this.appCode = appCode;
        this.appKey = appKey;
        this.supportUserLogin = supportUserLogin;
        this.supportUserPassword = supportUserPassword;
        this.tenantLogin = tenantLogin;
        this.type = type;
        this.openApis = openApis;
        this.sessionId = sessionId;
    }
}
