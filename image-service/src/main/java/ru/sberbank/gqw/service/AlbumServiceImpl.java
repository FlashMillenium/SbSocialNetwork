package ru.sberbank.gqw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sberbank.gqw.dto.AlbumDTO;
import ru.sberbank.gqw.dto.ImageDTO;
import ru.sberbank.gqw.model.AlbumEntity;
import ru.sberbank.gqw.model.ImageEntity;
import ru.sberbank.gqw.repository.AlbumRepository;
import ru.sberbank.gqw.repository.ImageRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final Logger logger = LoggerFactory.getLogger(AlbumService.class);

    private final EntityConverter converter;
    private final AlbumRepository albumRepository;
    private final ImageRepository imageRepository;
    private final ImageProcessor imageProcessor;

    @Autowired
    public AlbumServiceImpl(EntityConverter converter, AlbumRepository albumRepository, ImageRepository imageRepository, ImageProcessor imageProcessor) {
        this.converter = converter;
        this.albumRepository = albumRepository;
        this.imageRepository = imageRepository;
        this.imageProcessor = imageProcessor;
    }

    @Override
    public Page<AlbumDTO> getUserAlbums(Long userId, Pageable pageable) {
        Page<AlbumEntity> userAlbums = albumRepository.findAllByUserId(userId, pageable);
        Page<AlbumDTO> result = userAlbums.map(converter::convert);
        logger.debug("Return albums for user with id {}", userId);
        return result;
    }

    @Override
    public Page<ImageDTO> getImagesFromAlbum(Long albumId, Pageable pageable) {
        AlbumEntity album = albumRepository.getOne(albumId);
        Page<ImageEntity> imagesFromAlbum = imageRepository.findAllByAlbum(album, pageable);
        Page<ImageDTO> result = imagesFromAlbum.map(converter::convert);
        logger.debug("Return images from album with id {}", albumId);
        return result;
    }

    @Override
    public ResponseEntity<AlbumDTO> saveAlbum(AlbumDTO updateAlbum) {
        return validateNewAlbumData(updateAlbum).orElseGet(() -> saveAlbumToBase(updateAlbum));

    }

    private Optional<ResponseEntity<AlbumDTO>> validateNewAlbumData(AlbumDTO album) {
        String headerName = "AlbumMessage";
        if (Objects.nonNull(album.getId())
                && !albumRepository.exists(album.getId())) {
            ResponseEntity<AlbumDTO> error = generateErrorResponse(headerName, "When add new album you don't know id in my base.");
            logger.warn("Input album data with not existing id: {}", album);
            return Optional.of(error);
        }
        if (Objects.isNull(album.getUserId())) {
            ResponseEntity<AlbumDTO> error = generateErrorResponse(headerName, "User Id must present.");
            logger.warn("Input album data with empty user id: {}", album);
            return Optional.of(error);
        }
        if (Objects.isNull(album.getName()) || album.getName().length() == 0) {
            ResponseEntity<AlbumDTO> error = generateErrorResponse(headerName, "Album name must be set.");
            logger.warn("Input album data with empty album name: {}", album);
            return Optional.of(error);
        }
        return Optional.empty();
    }

    private <T> ResponseEntity<T> generateErrorResponse(String headerName, String headerValue) {
        HttpHeaders header = new HttpHeaders();
        header.add(headerName, headerValue);
        return new ResponseEntity<>(header, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<AlbumDTO> saveAlbumToBase(AlbumDTO updateAlbum) {
        AlbumEntity updateEntity = converter.convert(updateAlbum);
        AlbumEntity saveEntity = albumRepository.saveAndFlush(updateEntity);
        AlbumDTO result = converter.convert(saveEntity);
        logger.debug("Successfully save album data to base");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ImageDTO> updateImage(ImageDTO toUpdate) {
        return validateUpdateImageData(toUpdate).orElseGet(() -> saveImageToBase(toUpdate));
    }

    private Optional<ResponseEntity<ImageDTO>> validateUpdateImageData(ImageDTO image) {
        String headerName = "ImageMessage";
        if (Objects.nonNull(image.getId())
                && !imageRepository.exists(image.getId())) {
            ResponseEntity<ImageDTO> error = generateErrorResponse(headerName, "When add new image you don't know id in my base.");
            logger.warn("Input wrong image data with not existing id: {}", image);
            return Optional.of(error);
        }
        if (Objects.isNull(image.getAlbumId())
                || !albumRepository.exists(image.getAlbumId())) {
            ResponseEntity<ImageDTO> error = generateErrorResponse(headerName, "Album id is null or linked on unexisted album.");
            logger.warn("Input wrong image data with wrong albumId: {}", image);
            return Optional.of(error);
        }
        if (Objects.isNull(image.getImagePath()) || !image.getImagePath().exists()) {
            ResponseEntity<ImageDTO> error = generateErrorResponse(headerName, "Image path is empty or wrong.");
            logger.warn("Input wrong image data with wrong path to file: {}", image);
            return Optional.of(error);
        }
        if (Objects.isNull(image.getName()) || image.getName().length() == 0) {
            ResponseEntity<ImageDTO> error = generateErrorResponse(headerName, "Image name is empty or null.");
            logger.warn("Input wrong image data with wrong image name: {}", image);
            return Optional.of(error);
        }
        return Optional.empty();
    }

    private ResponseEntity<ImageDTO> saveImageToBase(ImageDTO updateImage) {
        ImageEntity updateEntity = converter.convert(updateImage);
        ImageEntity saveEntity = imageRepository.saveAndFlush(updateEntity);
        ImageDTO result = converter.convert(saveEntity);
        logger.debug("Successfully save image data to base");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<ImageDTO> addImage(Long albumId, String name, byte[] data) {
        if (Objects.isNull(albumId) || !albumRepository.exists(albumId)) {
            logger.warn("Input wrong image data with wrong albumId: {}", albumId);
            return generateErrorResponse("ImageMessage", "Album id is null or linked on unexisted album.");
        }
        if(Objects.isNull(name) || name.length() == 0){
            logger.warn("Input wrong image data with wrong image name: {}", name);
            return generateErrorResponse("ImageMessage", "Image name is empty or null.");
        }
        if(Objects.isNull(data) || data.length == 0){
            logger.warn("Input wrong image data with empty data array");
            return generateErrorResponse("ImageMessage", "Data array is empty or null.");
        }
        AlbumEntity album = albumRepository.getOne(albumId);
        Optional<ImageEntity> createdImage = imageProcessor.createImageEntity(album, name, data);
        if(!createdImage.isPresent()){
            return generateErrorResponse("ImageMessage", "Some problem with writing image file occurs");
        }
        ImageEntity newImage = createdImage.get();
        ImageEntity savedImage = imageRepository.saveAndFlush(newImage);
        logger.debug("Successfully add Image to base");
        ImageDTO result = converter.convert(savedImage);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> deleteAlbum(Long albumId) {
        albumRepository.delete(albumId);
        logger.debug("Delete album with id {}", albumId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteImage(ImageDTO deleteImage) {
        imageRepository.delete(deleteImage.getId());
        logger.debug("Delete image with id {}", deleteImage.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
