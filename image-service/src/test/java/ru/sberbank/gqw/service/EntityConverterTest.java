package ru.sberbank.gqw.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sberbank.gqw.dto.AlbumDTO;
import ru.sberbank.gqw.dto.ImageDTO;
import ru.sberbank.gqw.model.AlbumEntity;
import ru.sberbank.gqw.model.ImageEntity;
import ru.sberbank.gqw.repository.AlbumRepository;
import ru.sberbank.gqw.repository.ImageRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class EntityConverterTest {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private EntityConverter entityConverter;

    @Before
    public void setUp() throws MalformedURLException {
        AlbumEntity albumEntity = new AlbumEntity(5L, "testEntity");
        albumRepository.saveAndFlush(albumEntity);
        ImageEntity testImage = new ImageEntity("testImage", new File(""), new URL("http://localhost/"), albumEntity);
        imageRepository.saveAndFlush(testImage);
        albumEntity.setAlbumCover(testImage);
        testImage.setDescription("This description must equals copy object");
        albumRepository.saveAndFlush(albumEntity);
        imageRepository.saveAndFlush(testImage);
    }

    @Test
    @Transactional
    public void checkAlbumFromDtoToEntity() {
        AlbumEntity expected = albumRepository.findAll().get(0);
        AlbumDTO input = AlbumDTO.builder().id(expected.getId()).name(expected.getName()).userId(expected.getUserId())
                .albumCoverId(expected.getAlbumCover().getId()).build();
        AlbumEntity actual = entityConverter.convert(input);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getAlbumCover(), actual.getAlbumCover());
    }

    @Test
    public void checkAlbumFromEntityToDto() {
        AlbumEntity input = albumRepository.findAll().get(0);
        AlbumDTO expected = AlbumDTO.builder().id(input.getId()).name(input.getName()).userId(input.getUserId())
                .albumCoverId(input.getAlbumCover().getId()).build();
        AlbumDTO actual = entityConverter.convert(input);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getAlbumCoverId(), actual.getAlbumCoverId());
    }

    @Test
    @Transactional
    public void checkImageFromDtoToEntity() {
        ImageEntity expected = imageRepository.findAll().get(0);
        expected.setDescription("i'm input object!");
        ImageDTO input = ImageDTO.builder().id(expected.getId()).albumId(expected.getAlbum().getId())
                .imagePath(expected.getImagePath()).name(expected.getName())
                .description(expected.getDescription()).imageUrl(expected.getImageURL()).build();
        ImageEntity actual = entityConverter.convert(input);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAlbum(), actual.getAlbum());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getImagePath(), actual.getImagePath());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    public void checkImageFromEntityToDto() {
        ImageEntity input = imageRepository.findAll().get(0);
        ImageDTO expected = ImageDTO.builder().id(input.getId()).albumId(input.getAlbum().getId())
                .imagePath(input.getImagePath()).name(input.getName()).description(input.getDescription()).build();
        ImageDTO actual = entityConverter.convert(input);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAlbumId(), actual.getAlbumId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getImagePath(), actual.getImagePath());
        assertEquals(expected.getDescription(), actual.getDescription());
    }
}