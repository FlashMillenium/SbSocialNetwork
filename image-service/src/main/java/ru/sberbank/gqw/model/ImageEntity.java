package ru.sberbank.gqw.model;


import lombok.*;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "images")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "image_uri", nullable = false)
    private File imagePath;

    @NonNull
    @ManyToOne
    private AlbumEntity album;

    @Column(name = "description")
    @Lob
    private String description;
}
