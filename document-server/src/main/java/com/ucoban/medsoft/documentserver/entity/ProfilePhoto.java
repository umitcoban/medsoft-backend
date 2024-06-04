package com.ucoban.medsoft.documentserver.entity;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profile_photos")
public class ProfilePhoto {
    @Id
    private String id;

    private String name;

    private Binary image;

    private String userId;

    public ProfilePhoto() {
    }

    public ProfilePhoto(String name, Binary image, String userId) {
        this.name = name;
        this.image = image;
        this.userId = userId;
    }

    public ProfilePhoto(String id, String name, Binary image, String userId) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
