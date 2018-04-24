package ru.sberbank.gqw.service;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sberbank.gqw.dto.AlbumDTO;
import ru.sberbank.gqw.model.AlbumEntity;
import ru.sberbank.gqw.model.ImageEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ImageProcessor {
    @Value("${gqw.image.root-folder}")
    private String rootPath;
    private final static String ROOT_FOLDER = File.separator + "image";


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Optional<ImageEntity> createImageEntity(AlbumEntity albumEntity, String name, byte[] data) {
        File file = FileUtils.getFile( generatePath(albumEntity),name);
        try {
            FileUtils.writeByteArrayToFile(file, data);
        } catch (IOException e) {
            logger.error("Some problems with write data in file on path {}", file.getAbsolutePath(), e);
            return Optional.empty();
        }
        return Optional.of(new ImageEntity(name, file, albumEntity));
    }

    public String generatePath(AlbumEntity albumEntity) {
        return new StringBuilder(rootPath)
                .append(ROOT_FOLDER)
                .append(File.separator).append(albumEntity.getUserId())
                .append(File.separator).append(albumEntity.getId())
                .append(File.separator)
                .toString();
    }

    public String getStr() {
        String pathToFile = rootPath + "\\image\\35\\1\\";
        File file = FileUtils.getFile(pathToFile, "nginx.png");


        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            logger.debug("!!!!!!!!FROM LOGGER !!!!!!");
            System.out.println("!!!!!!!!!");
            System.out.println(bytes.length);
            FileUtils.writeByteArrayToFile(new File(rootPath + "\\image\\35\\2\\test.png"), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
