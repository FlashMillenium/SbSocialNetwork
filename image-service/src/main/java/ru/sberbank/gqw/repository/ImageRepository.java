package ru.sberbank.gqw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.gqw.model.AlbumEntity;
import ru.sberbank.gqw.model.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    Page<ImageEntity> findAllByAlbum(AlbumEntity albumEntity, Pageable pageable);
}
