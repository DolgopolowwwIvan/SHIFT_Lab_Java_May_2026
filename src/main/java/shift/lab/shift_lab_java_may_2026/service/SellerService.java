package shift.lab.shift_lab_java_may_2026.service;

import shift.lab.shift_lab_java_may_2026.model.Seller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface SellerService {

    List<Seller> findAll();

    Seller findById(Integer id);

    Seller create(Seller seller);

    Seller update(Integer id,
                  Seller seller);

    void delete(Integer id);

    Seller findTopSellerByPeriod(LocalDate date,
                                 String period);

    List<Seller> findSellersWithTotalAmountLessThan(BigDecimal amount,
                                                    LocalDate date,
                                                    String period);
}
