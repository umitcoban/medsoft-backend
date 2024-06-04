package com.ucoban.medsoft.documentserver.dao;

import com.ucoban.medsoft.documentserver.entity.ProfilePhoto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ProfilePhotoService {
    String save(MultipartFile file, String userId) throws IOException;
    ProfilePhoto findById(String id);
}
