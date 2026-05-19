package shift.lab.shift_lab_java_may_2026.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shift.lab.shift_lab_java_may_2026.exception.EntityNotFoundException;
import shift.lab.shift_lab_java_may_2026.model.Seller;
import shift.lab.shift_lab_java_may_2026.repository.SellerRepository;
import shift.lab.shift_lab_java_may_2026.repository.TransactionRepository;
import shift.lab.shift_lab_java_may_2026.service.PeriodCalculator;
import shift.lab.shift_lab_java_may_2026.service.SellerService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final TransactionRepository transactionRepository;
    private final PeriodCalculator periodCalculator;

    @Override
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    @Override
    public Seller findById(
            Integer id
    ){
        return sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Seller by id = " + id));
    }

    @Override
    @Transactional
    public Seller create(
            Seller seller
    ){
        seller.setRegistrationDate(LocalDateTime.now());
        Seller saved = sellerRepository.save(seller);

        log.info("Seller created successfully: id={}", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public Seller update(
            Integer id,
            Seller sellerDetails
    ){
        Seller seller = findById(id);
        seller.setName(sellerDetails.getName());
        seller.setContactInfo(sellerDetails.getContactInfo());
        Seller updated = sellerRepository.save(seller);

        log.info("Seller updated successfully: id={}", updated.getId());
        return updated;
    }

    @Override
    @Transactional
    public void delete(
            Integer id
    ){
        Seller seller = findById(id);

        if(transactionRepository.existsBySellerId(id)){
            throw new IllegalStateException("Cannot delete seller with existing transactions. Seller id: " + id);
        }

        sellerRepository.delete(seller);
        log.info("Seller deleted successfully: id={}", id);
    }

    @Override
    public Seller findTopSellerByPeriod(
            LocalDate date,
            String period
    ) {
        LocalDateTime start = periodCalculator.calculateStart(date, period);
        LocalDateTime end = LocalDateTime.now();

        return transactionRepository.findTopSellerByPeriod(start, end, PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "No top seller found for period: " + start + " - " + end));
    }

    @Override
    public List<Seller> findSellersWithTotalAmountLessThan(
            BigDecimal amount,
            LocalDate date,
            String period
    ) {
        LocalDateTime start = periodCalculator.calculateStart(date, period);
        LocalDateTime end = LocalDateTime.now();

        log.info("Finding sellers with total amount less than {} for period: {} to {}",
                amount, start, end);

        return transactionRepository.findSellersWithTotalAmountLessThan(amount, start, end);
    }

}