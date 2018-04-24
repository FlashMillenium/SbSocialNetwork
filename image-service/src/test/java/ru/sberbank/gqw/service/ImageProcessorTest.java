package ru.sberbank.gqw.service;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sberbank.gqw.ImageServiceApplication;
import ru.sberbank.gqw.model.AlbumEntity;
import ru.sberbank.gqw.model.ImageEntity;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageProcessorTest {
    @Autowired
    ImageProcessor ip;

    @Test
    public void checkFileIsWrittenInNeededFolder() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String file = classloader.getResource("nginx.png").getFile();
        byte[] bytes = FileUtils.readFileToByteArray(new File(file));
        AlbumEntity albumEntity = new AlbumEntity(5L, "TestAlbum");
        albumEntity.setId(42L);
        Optional<ImageEntity> imageEntity = ip.createImageEntity(albumEntity, "test.png", bytes);
        assertTrue(imageEntity.isPresent());
        assertTrue(imageEntity.get().getImagePath().exists());
        assertTrue(new File("target\\test-classes\\image\\5\\42\\test.png").exists());
    }
}