package com.platformcommons.platform.service.search.domain;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "commons_area_pincode")
public class CommonsAreaPinCode {
    @Id
    @Field
    private Long id;

    @Field
    private String areaCode;

    @Field
    private String districtCode;

    @Field
    private String stateCode;

    @Field
    private String countryCode;

    @Field
    private String districtLabel;

    @Field
    private String stateLabel;

    @Field
    private String countryLabel;

    @Field
    private String label;

    @Field
    private String alternativeLabel;

    @Builder
    public CommonsAreaPinCode(Long id, String areaCode, String districtCode, String stateCode,
                              String countryCode, String districtLabel, String stateLabel,
                              String countryLabel, String label, String alternativeLabel) {
        this.id = id;
        this.areaCode = areaCode;
        this.districtCode = districtCode;
        this.stateCode = stateCode;
        this.countryCode = countryCode;
        this.districtLabel = districtLabel;
        this.stateLabel = stateLabel;
        this.countryLabel = countryLabel;
        this.label = label;
        this.alternativeLabel = alternativeLabel;
    }

    public void update(CommonsAreaPinCode toBeUpdated) {
        if (toBeUpdated.areaCode != null) {
            this.areaCode = toBeUpdated.getAreaCode();
        }
        if (toBeUpdated.districtCode != null) {
            this.districtCode = toBeUpdated.getDistrictCode();
        }
        if (toBeUpdated.stateCode != null) {
            this.stateCode = toBeUpdated.getStateCode();
        }
        if (toBeUpdated.countryCode != null) {
            this.countryCode= toBeUpdated.getCountryCode();
        }
        if (toBeUpdated.districtLabel != null) {
            this.districtLabel = toBeUpdated.getDistrictLabel();
        }
        if (toBeUpdated.stateLabel != null) {
            this.stateLabel = toBeUpdated.getStateLabel();
        }
        if (toBeUpdated.countryLabel != null) {
            this.countryLabel = toBeUpdated.getCountryLabel();
        }
        if (toBeUpdated.label != null) {
            this.label = toBeUpdated.getLabel();
        }
        if (toBeUpdated.alternativeLabel != null) {
            this.alternativeLabel = toBeUpdated.getAlternativeLabel();
        }
    }
}
