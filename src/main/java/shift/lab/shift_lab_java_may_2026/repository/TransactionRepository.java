package shift.lab.shift_lab_java_may_2026.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shift.lab.shift_lab_java_may_2026.model.Seller;
import shift.lab.shift_lab_java_may_2026.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllBySellerId(Integer sellerId);

    boolean existsBySellerId(Integer id);

    @Query("""
            SELECT t.seller
            FROM Transaction t
            WHERE t.transactionDate BETWEEN :start AND :end
            GROUP BY t.seller
            ORDER BY SUM(t.amount) DESC
            """)
    List<Seller> findTopSellerByPeriod(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    @Query("""
            SELECT t.seller
            FROM Transaction t
            WHERE t.transactionDate BETWEEN :start AND :end
            GROUP BY t.seller
            HAVING SUM(t.amount) < :amount
            """)
    List<Seller> findSellersWithTotalAmountLessThan(
            @Param("amount") BigDecimal amount,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
