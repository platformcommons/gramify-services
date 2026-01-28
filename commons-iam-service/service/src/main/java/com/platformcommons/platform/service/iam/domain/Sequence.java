package com.platformcommons.platform.service.iam.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "Sequence.updateNameByNextVal", query = "update Sequence s set s.nextVal = s.nextVal+1 where s.name = :name"),
        @NamedQuery(name = "Sequence.getSequence", query = "select s.nextVal from Sequence s where s.name = :name")
})
@NoArgsConstructor
@Table(name = "__sequence")
@Getter
@Setter
public class Sequence {

    @Id
    private String name;

    private Long nextVal;

    @Builder
    public Sequence(String name, Long nextVal) {
        this.name = name;
        this.nextVal = nextVal;
    }
}
