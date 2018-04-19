package ru.sberbank.gqw.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.gqw.dto.AlbumDTO;
import ru.sberbank.gqw.dto.ImageDTO;

public interface AlbumService {
    public Page<AlbumDTO> getUserAlbums(Long userId, Pageable pageable);


    public Page<ImageDTO> getImagesFromAlbum(Long albumId, Pageable pageable);


    public RequestEntity<AlbumDTO> saveAlbum(AlbumDTO updateAlbum);

    public RequestEntity<?> deleteAlbum(Long albumId);


    public RequestEntity<?> deleteImage(ImageDTO deleteImage);

    public RequestEntity<ImageDTO> saveImage(Long albumId, String name, byte[] data);
}
