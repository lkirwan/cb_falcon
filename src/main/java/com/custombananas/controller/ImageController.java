package com.custombananas.controller;

import com.custombananas.dao.UploadFileRepository;
import com.custombananas.model.ImageStatus;
import com.custombananas.model.UploadFile;
import com.custombananas.service.ImageService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("image")
public class ImageController {

    private Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Autowired
    private UploadFileRepository fileRepository;

    @PostConstruct
    private void init() {
        logger.info("Connected to database: " + databaseName);
    }

    @GetMapping(value = "{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    @ResponseBody
    public byte[] getImage(@PathVariable String id) {
        try {
            Optional<UploadFile> fileOptional = fileRepository.findById(id);

            if (fileOptional.isPresent()) {
                return fileOptional.get().getContent().getData();
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Image not found for ID: %s", id));
        } catch (ConversionFailedException e) {
            logger.warn(e.getLocalizedMessage());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Image not found for ID: %s", id));
    }

    @PostMapping("upload")
    @ResponseStatus(HttpStatus.CREATED)
    public String convertImage(@RequestParam("targetExtension") String targetExtension, @RequestParam("image") MultipartFile file) throws IOException {

        UploadFile photo = new UploadFile(file.getOriginalFilename(), targetExtension);
        photo.setContent(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo.setStatus(ImageStatus.UPLOADED);
		photo.setContent(new Binary(BsonBinarySubType.BINARY, ImageService.convertImage(file, targetExtension).toByteArray()));
        photo.setStatus(ImageStatus.CONVERTED);
        photo = fileRepository.insert(photo);

        return photo.getId();
    }

}
