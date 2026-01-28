package com.platformcommons.platform.service.search.domain;

import com.platformcommons.platform.service.search.domain.base.ElasticBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;

import org.springframework.data.annotation.Id;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "education_institute")
public class EducationalInstituteV2 extends ElasticBaseEntity {

    @Id
    @Field
    private String code;

    @Field
    private String instituteName;

    @Field
    private String parentInstituteName;

    @Field
    private String parentInstituteCode;

    @Field
    private String instituteType;

    @Field
    private String parentInstituteType;

    @Field
    private String city;

    @Field
    private String state;

    @Field
    private String address;

    @Field
    private String website;

    @Builder
    public EducationalInstituteV2(Long tenantId, Boolean isActive, SeqNoPrimaryTerm SeqNoPrimaryTerm, String code, String instituteName,
                                  String parentInstituteName, String instituteType, String parentInstituteType, String city,
                                  String state, String address, String website, String parentInstituteCode) {
        super(tenantId, isActive, SeqNoPrimaryTerm);
        this.code = code;
        this.instituteName = instituteName;
        this.parentInstituteName = parentInstituteName;
        this.instituteType = instituteType;
        this.parentInstituteType = parentInstituteType;
        this.city = city;
        this.state = state;
        this.address = address;
        this.website = website;
        this.parentInstituteCode = parentInstituteCode;
    }

    public void init() {
        code = createEducationCode(instituteName);
        parentInstituteCode = createEducationCode(parentInstituteName);
    }

    private String createEducationCode(String name) {
        String nameModified = name.trim().replace(" ", "_").toUpperCase();
        if (nameModified.length() > 10) {
            nameModified = nameModified.substring(0, 10);
        }
        String random = generateRandomString();
        long timestamp = System.currentTimeMillis();
        return nameModified + "_" + random + "_" + timestamp;
    }

    private String generateRandomString() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 6)
                .toUpperCase();
    }
}
