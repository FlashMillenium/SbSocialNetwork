package ru.sberbank.gqw.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sberbank.gqw.dto.AlbumDTO;
import ru.sberbank.gqw.dto.ImageDTO;
import ru.sberbank.gqw.model.AlbumEntity;
import ru.sberbank.gqw.model.ImageEntity;
import ru.sberbank.gqw.repository.AlbumRepository;
import ru.sberbank.gqw.repository.ImageRepository;

import java.util.Objects;

@Component
public class EntityConverter {
    private final Logger logger = LoggerFactory.getLogger(EntityConverter.class);

    private final ModelMapper modelMapper = new ModelMapper();
    private ImageRepository imageRepository;
    private AlbumRepository albumRepository;

    @Autowired
    public EntityConverter(ImageRepository imageRepository, AlbumRepository albumRepository) {
        this.imageRepository = imageRepository;
        this.albumRepository = albumRepository;
    }

    public AlbumEntity convert(AlbumDTO albumDTO) {
        AlbumEntity result = modelMapper.map(albumDTO, AlbumEntity.class);
        setAlbumCover(albumDTO, result);
        logger.debug("Convert {} to {}", albumDTO, result);
        return result;
    }


    private void setAlbumCover(AlbumDTO albumDTO, AlbumEntity result) {
        if (Objects.nonNull(albumDTO.getAlbumCoverId())) {
            result.setAlbumCover(imageRepository.getOne(albumDTO.getAlbumCoverId()));
        }
    }

    public AlbumDTO convert(AlbumEntity albumEntity) {
        AlbumDTO result = modelMapper.map(albumEntity, AlbumDTO.class);
        logger.debug("Convert {} tp {}", albumEntity, result);
        return result;
    }

    public ImageEntity convert(ImageDTO imageDTO) {
        ImageEntity result = modelMapper.map(imageDTO, ImageEntity.class);
        result.setAlbum(albumRepository.getOne(imageDTO.getAlbumId()));
        logger.debug("Convert {} to {}", imageDTO, result);
        return result;
    }


    public ImageDTO convert(ImageEntity imageEntity) {
        ImageDTO result = modelMapper.map(imageEntity, ImageDTO.class);
        logger.debug("Convert {} to {}", imageEntity, result);
        return result;
    }


}
