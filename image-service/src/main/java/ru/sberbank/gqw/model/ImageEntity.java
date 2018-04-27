package ru.sberbank.gqw.model;


import lombok.*;

import javax.persistence.*;
import java.io.File;
import java.net.URL;

@Entity
@Table(name = "images")
@Getter
@Setter
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
    @Column(name = "image_path", nullable = false)
    private File imagePath;

    @NonNull
    @Column(name = "image_url", nullable = false)
    private URL imageURL;

    @NonNull
    @ManyToOne
    @JoinColumn(name="parent_id", nullable=false)
    private AlbumEntity album;

    @Column(name = "description")
    @Lob
    private String description;

    public String toString() {
        return "ImageEntity(id=" + this.getId() + ", name=" + this.getName() + ", imagePath=" + this.getImagePath() + ", albumId=" + this.getAlbum().getId() + ", description=" + this.getDescription() + ")";
    }
}
