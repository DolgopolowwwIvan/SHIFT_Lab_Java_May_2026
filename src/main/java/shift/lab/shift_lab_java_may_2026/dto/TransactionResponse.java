package shift.lab.shift_lab_java_may_2026.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shift.lab.shift_lab_java_may_2026.model.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private Integer id;
    private Integer sellerId;
    private String sellerName;
    private BigDecimal amount;
    private PaymentType paymentType;
    private LocalDateTime transactionDate;
}