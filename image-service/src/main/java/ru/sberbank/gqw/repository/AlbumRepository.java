package ru.sberbank.gqw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.gqw.model.AlbumEntity;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    Page<AlbumEntity> findAllByUserId(Long userId, Pageable pageable);
}
