package com.ucoban.medsoft.documentserver.dao.implementation;

import com.ucoban.medsoft.documentserver.dao.ProfilePhotoService;
import com.ucoban.medsoft.documentserver.entity.ProfilePhoto;
import com.ucoban.medsoft.documentserver.repository.ProfilePhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service("profilePhotoService")
public class ProfilePhotoServiceImpl implements ProfilePhotoService {

    private ProfilePhotoRepository profilePhotoRepository;

    public ProfilePhotoServiceImpl(ProfilePhotoRepository profilePhotoRepository) {
        this.profilePhotoRepository = profilePhotoRepository;
    }

    @Override
    public String save(MultipartFile file, String userId) throws IOException {
        var profilePhoto = new ProfilePhoto(UUID.randomUUID().toString(), new Binary(BsonBinarySubType.BINARY, file.getBytes()) , userId);
        var savedPhoto = profilePhotoRepository.insert(profilePhoto);
        return savedPhoto.getId();
    }

    @Override
    public ProfilePhoto findById(String id) {
        return profilePhotoRepository.findProfilePhotoByUserId(id).orElseThrow();
    }
}
