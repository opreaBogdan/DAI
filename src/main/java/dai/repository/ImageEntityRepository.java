package dai.repository;

import dai.entities.ImageEntity;
import dai.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ImageEntityRepository extends JpaRepository<ImageEntity, Long> {

    @Transactional
    @Query("select t from ImageEntity as t where t.username = ?1 AND t.price BETWEEN ?2 AND ?3 AND t.bought IS false")
    List<ImageEntity> getImagesForUser(String username, int minPrice, int maxPrice);

    @Transactional
    @Query("select t from ImageEntity as t where t.id = ?1")
    List<ImageEntity> getImage(long id);

    @Transactional
    @Query("select t from ImageEntity as t where t.bought = false")
    List<ImageEntity> getAllImages();

    @Modifying
    @Transactional
    @Query("update ImageEntity set bought = ?2 where id = ?1")
    void updatePictureAsBought(long id, boolean isBought);

}