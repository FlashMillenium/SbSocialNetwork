package ru.sberbank.front.services.allUser;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sberbank.gqw.dto.UserDTO;


@FeignClient(name = "user-service")
public interface ListUser {
    @GetMapping("/friends/{userLogin}")
    Page<UserDTO> getOtherUsers(@PathVariable(value = "userLogin") String userLogin);
}
