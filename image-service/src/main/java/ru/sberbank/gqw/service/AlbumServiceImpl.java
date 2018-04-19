package ru.sberbank.gqw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import ru.sberbank.gqw.dto.AlbumDTO;
import ru.sberbank.gqw.dto.ImageDTO;
import ru.sberbank.gqw.repository.AlbumRepository;
import ru.sberbank.gqw.repository.ImageRepository;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public Page<AlbumDTO> getUserAlbums(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ImageDTO> getImagesFromAlbum(Long albumId, Pageable pageable) {
        return null;
    }

    @Override
    public RequestEntity<AlbumDTO> saveAlbum(AlbumDTO updateAlbum) {
        return null;
    }

    @Override
    public RequestEntity<?> deleteAlbum(Long albumId) {
        return null;
    }

    @Override
    public RequestEntity<?> deleteImage(ImageDTO deleteImage) {
        return null;
    }

    @Override
    public RequestEntity<ImageDTO> saveImage(Long albumId, String name, byte[] data) {
        return null;
    }
}
