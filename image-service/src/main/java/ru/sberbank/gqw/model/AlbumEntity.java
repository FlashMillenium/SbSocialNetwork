package ru.sberbank.gqw.model;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "albums")
@Getter
@Setter
@EqualsAndHashCode(exclude = "albumCover")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @NonNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NonNull
    @Column(name = "album_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="cover_id")
    private ImageEntity albumCover;

    public String toString() {
        Long albumCoverId = Objects.nonNull(this.getAlbumCover()) ? this.getAlbumCover().getId() : null;
        return "AlbumEntity(id=" + this.getId() + ", userId=" + this.getUserId() + ", name=" + this.getName() +
                ", albumCoverId=" + albumCoverId  + ")";
    }
}
