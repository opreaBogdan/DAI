package dai.repository;

import dai.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    @Modifying
    @Transactional
    @Query("update UserEntity set password = ?2 where username = ?1")
    int updateUserInfo(String username, String password);

    @Modifying
    @Transactional
    @Query("update UserEntity set password = ?2 where token = ?1")
    int updatePasswordByToken(String token, String password);

    @Modifying
    @Transactional
    @Query("update UserEntity set token = ?2 where email = ?1")
    int updateTokenForRecover(String email, String token);

    @Modifying
    @Transactional
    @Query("update UserEntity set validated = true where token = ?1")
    int validate(String token);

    @Transactional
    @Query("select t from UserEntity as t where t.username = ?1 AND t.password = ?2")
    List<UserEntity> findUser(String username, String password);

    @Transactional
    @Query("select t from UserEntity as t where t.username = ?1 OR t.email = ?2")
    List<UserEntity> findUserRegister(String username, String email);
}