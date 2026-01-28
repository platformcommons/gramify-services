package  com.platformcommons.platform.service.blockprofile.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Embeddable identifier that links to another entity by business code and type.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LinkedId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "linked_id")
    private String linkedId;

    @Column(name = "linked_entity_type")
    private String linkedEntityType;
}
