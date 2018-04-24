package ru.sberbank.gqw.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sberbank.gqw.dto.AlbumDTO;
import ru.sberbank.gqw.dto.ImageDTO;
import ru.sberbank.gqw.model.AlbumEntity;
import ru.sberbank.gqw.repository.AlbumRepository;
import ru.sberbank.gqw.repository.ImageRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final Logger logger = LoggerFactory.getLogger(AlbumService.class);

    private EntityConverter converter;
    private AlbumRepository albumRepository;
    private ImageRepository imageRepository;

    @Autowired
    public AlbumServiceImpl(EntityConverter converter, AlbumRepository albumRepository, ImageRepository imageRepository) {
        this.converter = converter;
        this.albumRepository = albumRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public Page<AlbumDTO> getUserAlbums(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ImageDTO> getImagesFromAlbum(Long albumId, Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<AlbumDTO> saveAlbum(AlbumDTO updateAlbum) {
        return validateNewAlbumData(updateAlbum).orElseGet(() -> saveAlbumToBase(updateAlbum));

    }

    private Optional<ResponseEntity<AlbumDTO>> validateNewAlbumData(AlbumDTO updateAlbum) {
        String headerName = "AlbumMessage";
        if (Objects.nonNull(updateAlbum.getId())
                && !albumRepository.exists(updateAlbum.getId())) {
            HttpHeaders header = new HttpHeaders();
            header.add(headerName, "When add new album you don't know id in my base.");
            return Optional.of(new ResponseEntity<>(header, HttpStatus.BAD_REQUEST));
        }
        if (Objects.isNull(updateAlbum.getUserId())) {
            HttpHeaders header = new HttpHeaders();
            header.add(headerName, "User Id must present.");
            return Optional.of(new ResponseEntity<>(header, HttpStatus.BAD_REQUEST));
        }
        if (Objects.isNull(updateAlbum.getName()) || updateAlbum.getName().length() == 0) {
            HttpHeaders header = new HttpHeaders();
            header.add(headerName, "Album name must be set.");
            return Optional.of(new ResponseEntity<>(header, HttpStatus.BAD_REQUEST));
        }
        return Optional.empty();
    }

    private ResponseEntity<AlbumDTO> saveAlbumToBase(AlbumDTO updateAlbum) {
        AlbumEntity updateEntity = converter.convert(updateAlbum);
        AlbumEntity saveEntity = albumRepository.save(updateEntity);
        AlbumDTO result = converter.convert(saveEntity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteAlbum(Long albumId) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteImage(ImageDTO deleteImage) {
        return null;
    }

    @Override
    public ResponseEntity<ImageDTO> saveImage(Long albumId, String name, byte[] data) {
        return null;
    }
}
