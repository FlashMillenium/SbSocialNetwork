package ru.sberbank.gqw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.gqw.dto.AlbumDTO;
import ru.sberbank.gqw.dto.ImageDTO;
import ru.sberbank.gqw.service.AlbumService;

@RestController
@RequestMapping("/")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @GetMapping("/albums/{userId}")
    public Page<AlbumDTO> getUserAlbums(@PathVariable(value = "userId") Long userId, @PageableDefault Pageable pageable) {
        return albumService.getUserAlbums(userId, pageable);
    }

    @GetMapping("/images/{albumId}")
    public Page<ImageDTO> getImagesFromAlbum(@PathVariable(value = "albumId") Long albumId, @PageableDefault Pageable pageable) {
        return albumService.getImagesFromAlbum(albumId, pageable);
    }

    @PostMapping("/album")
    public ResponseEntity<AlbumDTO> saveAlbum(@RequestBody AlbumDTO album) {
        return albumService.saveAlbum(album);
    }

    @DeleteMapping("/albums")
    public ResponseEntity<?> deleteAlbum(@RequestBody Long albumId) {
        return albumService.deleteAlbum(albumId);
    }

    @DeleteMapping("/images")
    public ResponseEntity<?> deleteImage(@RequestBody ImageDTO deleteImage) {
        return albumService.deleteImage(deleteImage);
    }

    @PostMapping("/images/{albumId}/{name}")
    public ResponseEntity<ImageDTO> addImage(@PathVariable(value = "albumId") Long albumId, @PathVariable(value = "name")  String name, @RequestBody byte[] data) {
        return albumService.addImage(albumId, name, data);
    }
    @PostMapping("/images/")
    public ResponseEntity<ImageDTO> updateImage(@RequestBody ImageDTO toUpdate) {
        return albumService.updateImage(toUpdate);
    }
}
