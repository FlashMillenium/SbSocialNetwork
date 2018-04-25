package ru.sberbank.front.services.allUser;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sberbank.gqw.dto.UserDTO;


@FeignClient(name = "user-service")
public interface ListUser {

    @PostMapping("/friends/{userLogin}")
    RestResponsePage<UserDTO> getOtherUsers(@PathVariable(value = "userLogin") String userLogin, @PageableDefault Pageable pageable);

}
