package ru.sberbank.gqw.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.gqw.dto.AlbumDTO;
import ru.sberbank.gqw.dto.ImageDTO;

public interface AlbumService {
    public Page<AlbumDTO> getUserAlbums(Long userId, Pageable pageable);


    public Page<ImageDTO> getImagesFromAlbum(Long albumId, Pageable pageable);


    public ResponseEntity<AlbumDTO> saveAlbum(AlbumDTO updateAlbum);

    public ResponseEntity<?> deleteAlbum(Long albumId);


    public ResponseEntity<?> deleteImage(ImageDTO deleteImage);

    public ResponseEntity<ImageDTO> addImage(Long albumId, String name, byte[] data);

    public ResponseEntity<ImageDTO> updateImage(ImageDTO toUpdate);
}
