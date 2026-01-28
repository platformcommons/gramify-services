package com.platformcommons.platform.service.profile.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.person.dto.IeInfoVO;
import com.platformcommons.platform.service.profile.domain.DeliveryMode;
import com.platformcommons.platform.service.profile.domain.Ie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IeRepository extends BaseRepository<Ie, Long> {

    @Query("SELECT DISTINCT i FROM #{#entityName} i " +
            "WHERE (COALESCE(:worknodeIds, NULL) IS NULL OR i.taggedToWorknodeId IN :worknodeIds) " +
            "AND (COALESCE(:workforceIds, NULL) IS NULL OR i.taggedToWorkforceId IN :workforceIds) " +
            "AND ( " +
            "   ((COALESCE(:taggedToServiceAreaCodes, NULL) IS NULL AND :taggedToServiceAreaType IS NULL) " +
            "    OR (i.taggedToServiceAreaCode IN :taggedToServiceAreaCodes AND i.taggedToServiceAreaType = :taggedToServiceAreaType)) " +
            ") " +
            "AND i.isActive = true")
    Page<Ie> findByWorkforce(Set<Long> worknodeIds, Set<Long> workforceIds, Set<String> taggedToServiceAreaCodes, String taggedToServiceAreaType, Pageable pageable);

    @Query("SELECT NEW com.platformcommons.platform.service.person.dto.IeInfoVO(ie.id,pp.displayName,pcm.contactValue,pce.contactValue," +
            " ie.iconPic,ie.onTraderCenterId,ie.taggedToWorknodeId,ie.taggedToWorkforceId,ie.ieType ) " +
            " FROM #{#entityName} ie  JOIN ie.person p " +
            " LEFT JOIN p.personProfile pp " +
            " LEFT JOIN p.contactList pcm ON p.id=pcm.personId AND pcm.contactType = 'CONTACT_TYPE.MOBILE' AND pcm.isActive = 1 " +
            " LEFT JOIN p.contactList pce ON p.id=pce.personId AND pce.contactType = 'CONTACT_TYPE.EMAIL' AND pce.isActive = 1 " +
            " WHERE ie.isActive = 1  and ie.id = :id ")
    Optional<IeInfoVO> findInfoOfIeById(Long id);

    @Query("SELECT ie.deliveryModeList" +
            " FROM #{#entityName} ie  WHERE ie.isActive = 1  and ie.id = :id ")
    Set<DeliveryMode> findDeliveryModesByIeId(Long id);

    @Query("SELECT ie FROM #{#entityName} ie WHERE ie.isActive = 1 and ie.uuid = :uuid")
    Optional<Ie> findByUuid(@Param("uuid") String uuid);

    @Query("select (count(i) > 0) from Ie i where i.uuid in ?1")
    boolean existsByUuidIn(Collection<String> uuids);

    @Query(value = "SELECT DISTINCT i FROM #{#entityName} i JOIN i.person p" +
            " LEFT JOIN p.contactList pcm ON p.id=pcm.personId AND pcm.contactType = 'CONTACT_TYPE.MOBILE' AND pcm.isActive = 1 " +
            " WHERE pcm.contactValue = :mobile AND i.isActive = true ")
    Optional<Ie> findByMobile(String mobile);

}