package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class Authority extends BaseEntity {

    @Column(nullable = false, name = "code", unique = true, updatable = false)
    @Pattern(regexp = "^[A-Z-.]*$")
    @Id
    @Length(min = 3, max = 55)
    private String code;

    @Column(nullable = false, name = "authority_process")
    @Pattern(regexp = "^[A-Z-.]*$")
    @Length(min = 3, max = 55)
    private String process;

    @Column(name = "authority_description")
    private String authorityDescription;

    @Builder
    public Authority(String code, String authorityDescription, String process) {
        this.code = code;
        this.authorityDescription = authorityDescription;
        this.process = process;
    }


    public void update(Authority authorityToBeUpdated) {
        this.authorityDescription = authorityToBeUpdated.authorityDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return Objects.equals(this.code,authority.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Authority{" +
                "code='" + code + '\'' +
                '}';
    }
}
