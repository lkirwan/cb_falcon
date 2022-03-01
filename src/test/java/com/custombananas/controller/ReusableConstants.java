package com.custombananas.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class ReusableConstants {

    protected static final String FORMATTED_DATE_TODAY = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "T";
    protected static final String ID_IMAGE_VALID = "619fa7008fd4e1360f7f2db5";
    protected static final String ID_IMAGE_NOT_FOUND = "619eae6680659a44dd1f4c81";
    protected static final String ID_IMAGE_INVALID = "invalid_id";

    protected static final String TEST_FILE_NAME_PNG = "download-icon.png";
    protected static final String TEST_FILE_NAME_JPEG = "download-icon.jpeg";
    protected static final String TEST_FILE_NAME_PDF = "testFile.pdf";
    protected static final String EXTENSION_JPEG = "jpeg";
    protected static final String EXTENSION_PNG = "png";
    protected static final String EXTENSION_GIF = "gif";


    protected byte[] getFileBytes() throws IOException {
        ClassPathResource file = new ClassPathResource(TEST_FILE_NAME_PNG);
        return Files.readAllBytes(Paths.get(file.getURI()));
    }

    protected void assertJsonObjectErrorResponse(String response, HttpStatus httpStatus) {
        assertThat(JsonParser.parseString(response).getAsJsonObject().get("error").getAsString()).isEqualTo(httpStatus.getReasonPhrase());
    }

    protected void assertJsonObjectResponse(JsonObject returnedBody, String idImageInvalid) {
        assertThat(returnedBody.get("timestamp").getAsString()).contains(FORMATTED_DATE_TODAY);
        assertThat(returnedBody.get("status").getAsInt()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(returnedBody.get("error").getAsString()).isEqualTo(HttpStatus.NOT_FOUND.getReasonPhrase());
        assertThat(returnedBody.get("message").getAsString()).isEqualTo("");
        assertThat(returnedBody.get("path").getAsString()).isEqualTo("/image/" + idImageInvalid);
    }
}
