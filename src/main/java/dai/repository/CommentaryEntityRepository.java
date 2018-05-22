package dai.repository;

import dai.entities.CommentaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dragosbontea on 22/05/2018.
 */
@Repository
public interface CommentaryEntityRepository extends JpaRepository<CommentaryEntity, Long> {

    @Transactional
    @Query("select t from CommentaryEntity as t where t.photo = ?1")
    List<CommentaryEntity> getImagesForUser(long photoId);

    @Modifying
    @Transactional
    @Query(value = "insert into commentary (photo, author, commentary)  VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insertCommentaryToPicture( long photoId, String author, String commentary);

}
