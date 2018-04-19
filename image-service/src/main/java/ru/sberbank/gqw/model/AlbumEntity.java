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
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "album_name")
    private String name;

    @ManyToOne
    private ImageEntity albumCover;
}
