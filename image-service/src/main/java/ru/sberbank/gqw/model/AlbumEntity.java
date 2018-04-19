package ru.sberbank.gqw.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "albums")
@Getter
@Setter
@ToString
@EqualsAndHashCode
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
    private ImageEntity albumCover;
}
