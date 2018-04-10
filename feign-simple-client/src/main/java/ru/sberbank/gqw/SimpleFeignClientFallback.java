package ru.sberbank.gqw;

import org.springframework.stereotype.Component;

@Component
public class SimpleFeignClientFallback implements SimpleFeignClient {
    @Override
    public SimpleDto getById(Long id) {
        return new SimpleDto(0L, "i'm fallback message");
    }
}
