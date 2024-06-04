package com.ucoban.medsoft.documentserver.repository;

import com.ucoban.medsoft.documentserver.entity.ProfilePhoto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfilePhotoRepository extends MongoRepository<ProfilePhoto, String> {

    Optional<ProfilePhoto> findProfilePhotoByName(String name);
    Optional<ProfilePhoto> findProfilePhotoByUserId(String userId);
}
