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
public class AlbumDTO {
    private Long id;

    private Long userId;

    private String name;

    private Long albumCoverId;
}
