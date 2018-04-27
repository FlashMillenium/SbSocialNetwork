package ru.sberbank.gqw.dto;

import lombok.*;

import java.io.File;
import java.net.URL;

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

    private URL imageUrl;

    private Long albumId;

    private String name;

    private String description;
}
