package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import com.platformcommons.platform.service.iam.application.utility.CryptUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_detail",uniqueConstraints = {@UniqueConstraint(columnNames = {"app_code", "tenant_login"})})
public class AppDetail extends NonMultiTenantBaseTransactionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_code",updatable = false)
    private String appCode;

    @Column(name = "app_key", unique = true, updatable = false)
    private String appKey;

    @Column(name = "support_user_login")
    private String supportUserLogin;

    @Column(name = "support_user_password")
    @Convert(converter = CryptUtil.class)
    private String supportUserPassword;

    @Column(name = "tenant_login")
    private String tenantLogin;

    @Column(name = "type")
    private String type;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="app_open_apis",
            joinColumns = @JoinColumn(name =  "app_details_id"))
    private List<String> openApis;




    @Builder
    public AppDetail(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String appCode,
                     String appKey, List<String> openApis,String supportUserLogin, String supportUserPassword,String tenantLogin,
                     String type) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.appCode = appCode;
        this.appKey = appKey;
        this.openApis = openApis;
        this.supportUserLogin = supportUserLogin;
        this.supportUserPassword = supportUserPassword;
        this.tenantLogin = tenantLogin;
        this.type = type;
    }
}
