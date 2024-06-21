package com.ucoban.medsoft.documentserver.controller;

import com.ucoban.medsoft.documentserver.dao.ProfilePhotoService;
import com.ucoban.medsoft.documentserver.dto.ApiResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    ProfilePhotoService profilePhotoService;

    public PhotoController(@Qualifier("profilePhotoService") ProfilePhotoService profilePhotoService) {
        this.profilePhotoService = profilePhotoService;
    }

    @PostMapping("/profile")
    public ResponseEntity<ApiResponseDto<String>> createProfilePhoto(@RequestParam("file") MultipartFile file, @RequestParam("userId") @Validated String userId ) throws IOException {
        var id = profilePhotoService.save(file, userId);
        var image = Base64.getEncoder().encodeToString(profilePhotoService.findByUserId(userId).getImage().getData());
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK.value(), image, System.currentTimeMillis()));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponseDto<String>> getUserProfilPhoto(@PathVariable("userId") String userId ) throws IOException {
        var image = Base64.getEncoder().encodeToString(profilePhotoService.findByUserId(userId).getImage().getData());
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK.value(), image, System.currentTimeMillis()));
    }

    @DeleteMapping("/profile/{userId}")
    public ResponseEntity<ApiResponseDto<Boolean>> deleteUserProfilPhoto(@PathVariable("userId") String userId ) throws IOException {
        profilePhotoService.deletePhotoByUserId(userId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK.value(), true, System.currentTimeMillis()));
    }

}
