package shift.lab.shift_lab_java_may_2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shift.lab.shift_lab_java_may_2026.dto.TransactionRequest;
import shift.lab.shift_lab_java_may_2026.dto.TransactionResponse;
import shift.lab.shift_lab_java_may_2026.mapper.TransactionMapper;
import shift.lab.shift_lab_java_may_2026.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(
                transactionService.findAll().stream()
                        .map(TransactionMapper::toResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(
                TransactionMapper.toResponse(transactionService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestBody @Valid TransactionRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TransactionMapper.toResponse(
                        transactionService.create(TransactionMapper.toEntity(request))
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable("id") Integer id,
            @RequestBody @Valid TransactionRequest request
    ) {
        return ResponseEntity.ok(
                TransactionMapper.toResponse(
                        transactionService.update(id, TransactionMapper.toEntity(request))
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable("id") Integer id
    ) {
        transactionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsBySellerId(
            @PathVariable("sellerId") Integer sellerId
    ) {
        return ResponseEntity.ok(
                transactionService.findAllBySellerId(sellerId).stream()
                        .map(TransactionMapper::toResponse)
                        .toList()
        );
    }
}