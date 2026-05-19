package shift.lab.shift_lab_java_may_2026.service;

import shift.lab.shift_lab_java_may_2026.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    List<Transaction> findAll();

    Transaction findById(Integer id);

    Transaction create(Transaction transaction);

    Transaction update(Integer id, Transaction transaction);

    void delete(Integer id);

    List<Transaction> findAllBySellerId(Integer sellerId);
}
