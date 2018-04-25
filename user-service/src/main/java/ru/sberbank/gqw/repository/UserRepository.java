package ru.sberbank.gqw.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sberbank.gqw.model.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Page<UserEntity> findAllByLoginNot(String login, Pageable var);

    UserEntity getOneByLogin(String login);

    @Query("select uf from UserEntity u join u.friends uf where u.id= ?1")
    Page<UserEntity> getAllFriendsById(Long id, Pageable var1);

    boolean existsByLogin(String login);
}
