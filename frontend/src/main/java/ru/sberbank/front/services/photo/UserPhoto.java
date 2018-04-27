package ru.sberbank.front.services.photo;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.sberbank.gqw.dto.AlbumDTO;
import ru.sberbank.gqw.dto.ImageDTO;

@FeignClient(name = "image-service")
public interface UserPhoto {

    @PostMapping("/album")
    public ResponseEntity<AlbumDTO> saveAlbum(@RequestBody AlbumDTO album);

    @GetMapping("/albums/{userId}")
    public Page<AlbumDTO> getUserAlbums(@PathVariable(value = "userId") Long userId, @PageableDefault Pageable pageable);

    @GetMapping("/images/{albumId}")
    public Page<ImageDTO> getImagesFromAlbum(@PathVariable(value = "albumId") Long albumId, @PageableDefault Pageable pageable);

//    @PostMapping("/images/{albumId}")
//    ResponseEntity<ImageDTO> addImage(@PathVariable(value = "albumId") Long albumId, @RequestBody String name, @RequestBody byte[] data);
}
