package shift.lab.shift_lab_java_may_2026.mapper;

import shift.lab.shift_lab_java_may_2026.dto.TransactionRequest;
import shift.lab.shift_lab_java_may_2026.dto.TransactionResponse;
import shift.lab.shift_lab_java_may_2026.model.Seller;
import shift.lab.shift_lab_java_may_2026.model.Transaction;

public class TransactionMapper {

    public static TransactionResponse toResponse(
            Transaction transaction
    ) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .sellerId(transaction.getSeller().getId())
                .sellerName(transaction.getSeller().getName())
                .amount(transaction.getAmount())
                .paymentType(transaction.getPaymentType())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }

    public static Transaction toEntity(
            TransactionRequest request
    ) {
        Seller seller = new Seller();
        seller.setId(request.getSellerId());

        return Transaction.builder()
                .seller(seller)
                .amount(request.getAmount())
                .paymentType(request.getPaymentType())
                .transactionDate(request.getTransactionDate())
                .build();
    }
}