package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.iam.dto.brbase.UserContactDTO;
import com.platformcommons.platform.service.sdk.person.domain.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_login", "tenant_id"})})
public class User extends BaseTransactionalEntity {

    @Id
    @GenericGenerator(name = "sequence_user_id", strategy = "com.platformcommons.platform.service.iam.application.config.TLDIdentifierGenerator")
    @GeneratedValue(generator = "sequence_user_id")
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, name = "user_login")
    private String userLogin;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserContact> userContacts;

    @ManyToOne
    @JoinColumn(name = "tenant_id",insertable = false,updatable = false)
    private Tenant tenant;

    @OneToMany(mappedBy = "user")
    @BatchSize(size = 100)
    private Set<UserRoleMap> userRoleMaps;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;



    @Builder
    public User(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                Long id, String userLogin, String firstName, String lastName, Set<UserRoleMap> userRoleMaps,Tenant tenant,Set<UserContact> userContacts,String status,Person person) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.userLogin = userLogin;
        this.tenant = tenant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRoleMaps = userRoleMaps;
        this.userContacts = userContacts;
        this.status = status;
        if(this.userContacts!=null)
            this.userContacts.forEach(userContact -> userContact.setUser(this));
        this.person = person;
    }

    public static User mapToUser(Map<String, Object> map) {
        return User.builder()
                   .id(map.get("user_id")==null?null:Long.parseLong(map.get("user_id").toString()))
                   .userLogin( map.get("user_login")==null?null:map.get("user_login").toString() )
                   .firstName( map.get("first_name")==null?null:map.get("first_name").toString() )
                   .lastName(  map.get("last_name")==null?null:map.get("last_name").toString()  )
                   .build();

    }

    public String getUserName() {
        return firstName + " " + (lastName==null?"":" "+lastName);
    }

    public void addUserContact(UserContact userContact) {
        if(userContacts==null) userContacts = new LinkedHashSet<>();
        userContacts.add(userContact);
    }

    /**
     * This constructor is used by JPA to provide selected projections for getting user id reference
     * Don't change sequence of param.
     */

    public User(Long id) {
        this.id = id;
    }

    public User(Long id,String status) {
        this.id = id;
        this.status = status;
    }

    public void patch(User toBeUpdated) {
        if (toBeUpdated.getFirstName() != null) {
            this.firstName = toBeUpdated.getFirstName();
        }
        if (toBeUpdated.getLastName() != null) {
            this.lastName = toBeUpdated.getLastName();
        }
        if(!CollectionUtils.isEmpty(toBeUpdated.getUserContacts())){
            Map<Integer, UserContact> userContactDTOMap =  this.userContacts.stream().collect(Collectors.toMap(UserContact::getId, Function.identity()));
            toBeUpdated.getUserContacts().forEach(userContact -> {
                if(userContactDTOMap.containsKey(userContact.getId())){
                    userContactDTOMap.get(userContact.getId()).patch(userContact);
                }else{
                    if(Objects.nonNull(userContact.getId()) && userContact.getId()!=0L ){
                        throw new InvalidInputException("Something is fishy");
                    }
                    userContact.setUser(this);
                    this.addUserContact(userContact);
                    userContact.init();
                }
            });
        }
        if(toBeUpdated.getPerson()!=null){
            if(this.person==null){
                this.person = toBeUpdated.getPerson();
                toBeUpdated.getPerson().init();
            }
            else{
                this.person.patch(toBeUpdated.getPerson());
            }
        }
    }
}
