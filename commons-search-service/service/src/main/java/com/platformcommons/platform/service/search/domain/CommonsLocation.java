package com.platformcommons.platform.service.search.domain;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "commons_location")
public class CommonsLocation extends SolrBaseEntity {

    @Id
    @Field
    private String id;

    @Field
    private String stateCode;

    @Field
    private String stateLabel;

    @Field
    private String countryCode;

    @Field
    private String countryLabel;

    @Field
    private String cityCode;

    @Field
    private String cityLabel;

    @Field
    private String districtCode;

    @Field
    private String districtLabel;

    @Field
    private String blockCode;

    @Field
    private String blockLabel;

    @Field
    private String addressLabel;

    @Field
    private Double latitude;

    @Field
    private Double longitude;

    @Field
    private String locationCoordinates;

    @Field
    private String emojiCode;


    @Transient
    private String locationType; // derived field

    public String getLocationType() {
        if (cityCode != null) return "CITY";
        if (districtCode != null) {
            if (blockCode != null) return "BLOCK";
            return "DISTRICT";
        }
        if (stateCode != null) return "STATE";
        if (countryCode != null) return "COUNTRY";
        return "UNKNOWN";
    }


    @Builder
    public CommonsLocation(Long createdTimestamp, Long lastModifiedTimestamp, String id, String stateCode, String countryCode,
                           String cityCode, String districtCode, String blockCode, String addressLabel, String cityLabel, String stateLabel,
                           String countryLabel, String districtLabel, String blockLabel, Double latitude, Double longitude, 
                           String locationCoordinates, String emojiCode) {
        super(createdTimestamp, lastModifiedTimestamp);
        this.id = id;
        this.stateCode = stateCode;
        this.countryCode = countryCode;
        this.cityCode = cityCode;
        this.districtCode = districtCode;
        this.blockCode = blockCode;
        this.addressLabel = addressLabel;
        this.cityLabel = cityLabel;
        this.stateLabel = stateLabel;
        this.countryLabel = countryLabel;
        this.districtLabel = districtLabel;
        this.blockLabel = blockLabel;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationCoordinates = locationCoordinates;
        this.emojiCode = emojiCode;
        this.locationType = getLocationType();
    }

    public void init() {
        this.setCreatedTimestamp();
        this.setLastModifiedTimestamp();
        generatedRandomId();
    }

    private void generatedRandomId() {
        UUID uuid =  UUID.randomUUID();
        this.id = uuid.toString();
    }

}
