package shift.lab.shift_lab_java_may_2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shift.lab.shift_lab_java_may_2026.model.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {
}
