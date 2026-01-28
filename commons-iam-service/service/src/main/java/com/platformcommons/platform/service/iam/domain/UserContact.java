package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.sdk.person.domain.Contact;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "user_contact")
@Getter @Setter @NoArgsConstructor
public class UserContact extends BaseTransactionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean primaryContact;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Builder
    public UserContact(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Integer id, Boolean primaryContact, User user, Contact contact) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.primaryContact = primaryContact;
        this.user = user;
        this.contact = contact;
    }

    public void patch(UserContact userContact) {
        // Patch Need to be looked weather need to update verification status on update
    }

    public void init() {
        this.id = null;
    }
}