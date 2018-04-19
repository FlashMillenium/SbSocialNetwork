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
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "image_uri", nullable = false)
    private File imagePath;

    @ManyToOne
    private AlbumEntity album;

    @Column(name = "description")
    @Lob
    private String description;
}
