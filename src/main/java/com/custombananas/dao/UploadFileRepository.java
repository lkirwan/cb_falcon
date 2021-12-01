package com.custombananas.dao;

import com.custombananas.model.UploadFile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UploadFileRepository extends MongoRepository<UploadFile, String> {
}
