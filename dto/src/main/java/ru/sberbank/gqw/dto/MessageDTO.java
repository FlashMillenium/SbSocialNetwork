package ru.sberbank.gqw.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private int id;
    private int senderId;
    private int recipientId;
    private String message;
    private LocalDateTime sentAt;
    private boolean seen;
}
