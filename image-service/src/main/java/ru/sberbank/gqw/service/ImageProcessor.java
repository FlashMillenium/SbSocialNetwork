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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service
public class ImageProcessor {
    @Value("${gqw.image.root-folder}")
    private String rootPath;

    @Value("${gqw.image.root-path}")
    private String rootUri;

    private final static String ROOT_FOLDER = "image";


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Optional<ImageEntity> createImageEntity(AlbumEntity albumEntity, String name, byte[] data) {
        File file = FileUtils.getFile(generatePath(rootPath, albumEntity, File.separator), name);
        try {
            FileUtils.writeByteArrayToFile(file, data);
        } catch (IOException e) {
            logger.error("Some problems with write data in file on path {}", file.getAbsolutePath(), e);
            return Optional.empty();
        }
        URL url;
        try {
            URL root = new URL(generatePath("http://" + rootUri, albumEntity, "/"));
            url = new URL(root, name);
        } catch (MalformedURLException e) {
            logger.error("Some problems with generate URL", e);
            return Optional.empty();
        }
        return Optional.of(new ImageEntity(name, file, url, albumEntity));
    }

    private String generatePath(String root, AlbumEntity albumEntity, String separator) {
        return new StringBuilder(root)
                .append(separator).append(ROOT_FOLDER)
                .append(separator).append(albumEntity.getUserId())
                .append(separator).append(albumEntity.getId())
                .append(separator)
                .toString();

    }


}
