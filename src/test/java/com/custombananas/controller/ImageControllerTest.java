package com.custombananas.controller;

import com.custombananas.dao.UploadFileRepository;
import com.custombananas.model.UploadFile;
import com.custombananas.service.ImageService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

//@WebMvcTest(ImageController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageControllerTest extends ReusableConstants {

    @MockBean
    private UploadFileRepository fileRepository;
    @MockBean
    private ImageService imageService;

    @Autowired
    private TestRestTemplate template;

    @BeforeAll
    private void suiteSetup() throws IOException {
        UploadFile testUploadFile = new UploadFile(TEST_FILE_NAME_JPEG, EXTENSION_PNG);
        testUploadFile.setContent(new Binary(BsonBinarySubType.BINARY, getFileBytes()));

        when(fileRepository.findById(ID_IMAGE_VALID)).thenReturn(Optional.of(testUploadFile));
    }

    @Test
    public void getImage_imageRetrieved() {

        ResponseEntity<String> response = template.getForEntity("/image/" + ID_IMAGE_VALID, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//		assertJsonObjectResponse(JsonParser.parseString(response.getBody()).getAsJsonObject(), ID_IMAGE_VALID);
    }
}
