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
@RequiredArgsConstructor
public class AlbumDTO {
    private Long id;

    @NonNull
    private Long userId;

    @NonNull
    private String name;

    private Long albumCoverId;
}
