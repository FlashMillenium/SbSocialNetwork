package ru.sberbank.front.services.photo;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.sberbank.front.services.RestResponsePage;
import ru.sberbank.gqw.dto.AlbumDTO;
import ru.sberbank.gqw.dto.ImageDTO;

@FeignClient(name = "image-service")
public interface UserPhoto {

    @PostMapping("/album")
    ResponseEntity<AlbumDTO> saveAlbum(@RequestBody AlbumDTO album);

    @PostMapping("/albums/{userId}")
    RestResponsePage<AlbumDTO> getUserAlbums(@PathVariable(value = "userId") Long userId, @PageableDefault Pageable pageable);

    @PostMapping("/images/{albumId}")
    RestResponsePage<ImageDTO> getImagesFromAlbum(@PathVariable(value = "albumId") Long albumId, @PageableDefault Pageable pageable);

    @PutMapping("/images/{albumId}/{name}")
    ResponseEntity<ImageDTO> addImage(@PathVariable(value = "albumId") Long albumId, @PathVariable(value = "name") String name, @RequestBody byte[] data);
}
