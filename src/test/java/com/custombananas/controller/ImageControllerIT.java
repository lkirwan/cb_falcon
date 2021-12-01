package com.custombananas.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageControllerIT extends ReusableConstants {

    @Autowired
    private TestRestTemplate template;

    @BeforeAll
    public void suiteSetup() {
        //TODO: complete
//        template.postForEntity("/image/upload", String.class, MultipartFile.class);

    }

    @Test
    public void getImage_imageRetrieved() {
        //TODO: complete
//        fail("not yet implemented");
    }

    @Test
    public void getImage_notFound() {
        ResponseEntity<String> response = template.getForEntity("/image/" + ID_IMAGE_NOT_FOUND, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertJsonObjectResponse(JsonParser.parseString(response.getBody()).getAsJsonObject(), ID_IMAGE_NOT_FOUND);
    }

    @Test
    public void getImage_invalidId() {
        ResponseEntity<String> response = template.getForEntity("/image/" + ID_IMAGE_INVALID, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertJsonObjectResponse(JsonParser.parseString(response.getBody()).getAsJsonObject(), ID_IMAGE_INVALID);
    }

}
