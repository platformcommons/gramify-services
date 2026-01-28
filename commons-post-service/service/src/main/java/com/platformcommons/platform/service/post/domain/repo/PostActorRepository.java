package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.post.domain.PostActor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostActorRepository extends BaseRepository<PostActor, Long> {

    @Query("FROM #{#entityName} pa WHERE pa.actorId=:actorId  AND pa.actorType=:actorType")
    Optional<PostActor> getOrAddForCurrentUser(Long actorId, String actorType);


    @Modifying
    @Query("UPDATE #{#entityName} pa SET pa.name = :name , pa.iconpic = :iconpic, pa.contactNumber = :contactNumber, " +
            " pa.username = :username " +
            " where pa.actorId = :actorId AND " +
            " pa.actorType = :actorType ")
    void updatePostActorDetails(Long actorId, String actorType, String name, String iconpic,String contactNumber,String username);
}