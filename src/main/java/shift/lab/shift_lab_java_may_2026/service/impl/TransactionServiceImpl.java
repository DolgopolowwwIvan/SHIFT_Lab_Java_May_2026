package shift.lab.shift_lab_java_may_2026.service.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shift.lab.shift_lab_java_may_2026.exception.EntityNotFoundException;
import shift.lab.shift_lab_java_may_2026.model.Seller;
import shift.lab.shift_lab_java_may_2026.model.Transaction;
import shift.lab.shift_lab_java_may_2026.repository.SellerRepository;
import shift.lab.shift_lab_java_may_2026.repository.TransactionRepository;
import shift.lab.shift_lab_java_may_2026.service.TransactionService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction findById(
            Integer id
    ) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Transaction by id = " + id));
    }

    @Override
    @Transactional
    public Transaction create(
            Transaction transaction
    ) {
        Integer sellerId = transaction.getSeller().getId();

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Not found Seller by id = " + sellerId));

        transaction.setSeller(seller);

        Transaction saved = transactionRepository.save(transaction);
        log.info("Transaction created successfully: id={}", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public Transaction update(
            Integer id,
            Transaction transactionDetails
    ) {
        Transaction transaction = findById(id);

        Integer sellerId = transactionDetails.getSeller().getId();
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Not found Seller by id = " + sellerId));

        transaction.setAmount(transactionDetails.getAmount());
        transaction.setPaymentType(transactionDetails.getPaymentType());
        transaction.setSeller(seller);

        if (transactionDetails.getTransactionDate() != null) {
            transaction.setTransactionDate(transactionDetails.getTransactionDate());
        }

        Transaction updated = transactionRepository.save(transaction);
        log.info("Transaction updated successfully: id={}", updated.getId());
        return updated;
    }

    @Override
    @Transactional
    public void delete(
            Integer id
    ) {
        Transaction transaction = findById(id);
        transactionRepository.delete(transaction);

        log.info("Transaction deleted successfully: id={}", id);
    }

    @Override
    public List<Transaction> findAllBySellerId(
            Integer sellerId
    ) {
        return transactionRepository.findAllBySellerId(sellerId);
    }
}