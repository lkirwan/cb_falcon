package com.custombananas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageService {

    public static ByteArrayOutputStream convertImage(MultipartFile file, String targetExtension) throws IOException {
        Logger logger = LoggerFactory.getLogger(ImageService.class);

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, targetExtension, byteArrayOut);

        logger.info(String.format("Converted file %s to %s", file.getOriginalFilename(), targetExtension));

        return byteArrayOut;
    }
}
