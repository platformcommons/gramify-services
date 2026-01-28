package com.platformcommons.platform.service.iam.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "extra_attribute")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ExtraAttribute  extends AuthBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "attribute_code")
    private String attributeCode;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExtraAttributeValue> attributeValues;

    @Builder
    public ExtraAttribute(Long createdTimestamp, Long lastModifiedTimestamp,
                          Long id, String attributeCode, Set<ExtraAttributeValue> attributeValues) {
        super(createdTimestamp, lastModifiedTimestamp);
        this.id = id;
        this.attributeCode = attributeCode;
        this.attributeValues = attributeValues;
    }


    public void init() {
        this.id = 0L;
    }

    public void putUpdate(ExtraAttribute toBeUpdated) {
        this.attributeCode = toBeUpdated.getAttributeCode();
        if (this.attributeValues != null && toBeUpdated.getAttributeValues() != null) {
            this.attributeValues.removeIf(existingValue ->
                    toBeUpdated.getAttributeValues().stream()
                            .noneMatch(newValue -> newValue.getId().equals(existingValue.getId()))
            );
        }
        if (toBeUpdated.getAttributeValues() != null && !toBeUpdated.getAttributeValues().isEmpty()) {
            this.getAttributeValues().forEach(attributeValue -> {
                toBeUpdated.getAttributeValues().forEach(value -> {
                    if (attributeValue.getId().equals(value.getId())) {
                        attributeValue.putUpdate(value);
                    }
                });
            });
            toBeUpdated.getAttributeValues().forEach(toBeSaved -> {
                if (toBeSaved.getId() == null || toBeSaved.getId().equals(0L)) {
                    toBeSaved.init();
                    this.getAttributeValues().add(toBeSaved);
                }
            });
        }
    }


    public void patchUpdate(ExtraAttribute toBeUpdated) {
        this.attributeCode = toBeUpdated.getAttributeCode();
        if (toBeUpdated.getAttributeValues() != null && !toBeUpdated.getAttributeValues().isEmpty()) {
            if (this.attributeValues != null) {
                this.attributeValues.removeIf(existingValue ->
                        toBeUpdated.getAttributeValues().stream()
                                .noneMatch(newValue -> newValue.getId().equals(existingValue.getId()))
                );
            }
            this.getAttributeValues().forEach(attributeValue -> {
                toBeUpdated.getAttributeValues().forEach(value -> {
                    if (attributeValue.getId().equals(value.getId())) {
                        attributeValue.patchUpdate(value);
                    }
                });
            });
            toBeUpdated.getAttributeValues().forEach(toBeSaved -> {
                if (toBeSaved.getId() == null || toBeSaved.getId().equals(0L)) {
                    toBeSaved.init();
                    this.getAttributeValues().add(toBeSaved);
                }
            });
        }
    }
}
