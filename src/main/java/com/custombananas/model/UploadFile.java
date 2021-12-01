package com.custombananas.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.Objects;

@Document
public class UploadFile {

    @Id
    @MongoId(value = FieldType.OBJECT_ID)
    private String id;
    private String originalName;
    //    private String updatedName;
    private Date createdtime;
    private Binary content;
    private String targetExtension;
    private String status;

    public UploadFile(String originalName, String targetExtension) {
        this.originalName = originalName;
        this.targetExtension = targetExtension;
        this.createdtime = new Date(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Binary getContent() {
        return content;
    }

    public void setContent(Binary content) {
        this.content = content;
    }

    public ImageStatus getStatus() {
        return ImageStatus.fromValue(status);
    }

    public void setStatus(ImageStatus status) {
        this.status = status.toValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UploadFile)) return false;
        UploadFile that = (UploadFile) o;
        return Objects.equals(id, that.id) && Objects.equals(originalName, that.originalName) && Objects.equals(createdtime, that.createdtime) && Objects.equals(content, that.content) && Objects.equals(targetExtension, that.targetExtension) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalName, createdtime, content, targetExtension, status);
    }
}

