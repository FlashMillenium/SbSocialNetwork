package ru.sberbank.front.services.allUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sberbank.gqw.dto.UserDTO;

/**
 * TODO: comment
 *
 * @author Dmitriy Bobrov (bobrov.dmitriy@gmail.com)
 * @since 25.04.2018
 */

@Service
public class ListUserService {
    @Autowired
    private ListUser listUser;

    @PostMapping("/friends/{userLogin}")
    public Page<UserDTO> getOtherUsers(String userLogin, @PageableDefault
            Pageable pageable) {
        return listUser.getOtherUsers(userLogin, pageable);
    }
}
