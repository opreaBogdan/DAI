package dai.repository;

import dai.entities.ImageEntity;
import dai.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Long> {

    @Transactional
    @Query("select t from TransactionEntity as t where t.username = ?1")
    List<TransactionEntity> getTransactionsForUser(String username);
}