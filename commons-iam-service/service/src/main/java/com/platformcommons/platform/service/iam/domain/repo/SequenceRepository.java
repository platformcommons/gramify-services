package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.iam.domain.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SequenceRepository extends JpaRepository<Sequence, String> {
    @Transactional
    @Modifying
    @Query("update Sequence s set s.nextVal = s.nextVal+1 where s.name = ?1")
    int updateNameByNextVal(String name);

}