package ru.sberbank.gqw.dto;

import lombok.*;

import java.io.File;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Long id;

    private File imagePath;

    private AlbumDTO album;

    private String description;
}
