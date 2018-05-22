package dai.repository;

import dai.entities.TransactionEntity;
import dai.entities.Transaction_ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface Transaction_ImageEntityRepository extends JpaRepository<Transaction_ImageEntity, Long> {

    @Transactional
    @Query("select t from Transaction_ImageEntity as t where t.transaction_id = ?1")
    List<Transaction_ImageEntity> getImagesForTransaction(long transaction_id);
}