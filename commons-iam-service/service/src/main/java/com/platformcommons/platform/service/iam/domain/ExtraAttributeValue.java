package com.platformcommons.platform.service.iam.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "extra_attribute_value")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ExtraAttributeValue extends   AuthBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "data_code")
    private String dataCode;

    @Column(name = "data_value", columnDefinition = "TEXT")
    private String dataValue;

    @Column(name = "sequence")
    private Long sequence;


    @Builder
    public ExtraAttributeValue(Long createdTimestamp, Long lastModifiedTimestamp, Long id, String dataCode, String dataValue, Long sequence) {
        super(createdTimestamp, lastModifiedTimestamp);
        this.id = id;
        this.dataCode = dataCode;
        this.dataValue = dataValue;
        this.sequence = sequence;
    }

    public void init() {
        this.id = 0L;
    }

    public void putUpdate(ExtraAttributeValue toBeUpdated) {
        this.dataCode = toBeUpdated.getDataCode();
        this.dataValue = toBeUpdated.getDataValue();
        this.sequence = toBeUpdated.getSequence();
    }

    public void patchUpdate(ExtraAttributeValue toBeUpdated) {
        if (toBeUpdated.getDataCode() != null) {
            this.dataCode = toBeUpdated.getDataCode();
        }
        if (toBeUpdated.getDataValue() != null) {
            this.dataValue = toBeUpdated.getDataValue();
        }
        if (toBeUpdated.getSequence() != null) {
            this.sequence = toBeUpdated.getSequence();
        }
    }
}
