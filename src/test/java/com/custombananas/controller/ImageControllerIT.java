package com.custombananas.controller;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageControllerIT extends ReusableConstants {

    @Autowired
    private TestRestTemplate template;

    @BeforeAll
    public static void suiteSetup() {
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
        assertJsonObjectResponse(JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject(), ID_IMAGE_NOT_FOUND);
    }

    @Test
    public void getImage_invalidId() {
        ResponseEntity<String> response = template.getForEntity("/image/" + ID_IMAGE_INVALID, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertJsonObjectResponse(JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject(), ID_IMAGE_INVALID);
    }

    @Test
    public void convertImage_invalidFileType() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add("image", new File(TEST_FILE_NAME_PDF));
        map.add("targetExtension", EXTENSION_GIF);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(map, headers);

        ResponseEntity<String> response = template.postForEntity("/image/upload", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);

        assertJsonObjectErrorResponse(response.getBody(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

}
