package shift.lab.shift_lab_java_may_2026.integration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import shift.lab.shift_lab_java_may_2026.model.PaymentType;
import shift.lab.shift_lab_java_may_2026.model.Seller;
import shift.lab.shift_lab_java_may_2026.model.Transaction;
import shift.lab.shift_lab_java_may_2026.repository.SellerRepository;
import shift.lab.shift_lab_java_may_2026.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TransactionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void createTransaction_shouldSaveTransaction_whenSellerExists() throws Exception {
        Seller seller = sellerRepository.save(Seller.builder()
                .name("Test Seller")
                .contactInfo("test@example.com")
                .registrationDate(LocalDateTime.now())
                .build());

        var request = String.format("""
                {
                    "sellerId": %d,
                    "amount": 500.00,
                    "paymentType": "CARD",
                    "transactionDate": "2026-05-15T10:30:00"
                }
                """, seller.getId());

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(500.00));

        assertThat(transactionRepository.count()).isEqualTo(1);
    }

    @Test
    void createTransaction_shouldReturnNotFound_whenSellerDoesNotExist() throws Exception {
        var request = """
                {
                    "sellerId": 9999,
                    "amount": 500.00,
                    "paymentType": "CARD",
                    "transactionDate": "2026-05-15T10:30:00"
                }
                """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTransactionsBySellerId_shouldReturnSellerTransactions() throws Exception {
        Seller seller = sellerRepository.save(Seller.builder()
                .name("Seller For Tx")
                .contactInfo("seller@example.com")
                .registrationDate(LocalDateTime.now())
                .build());

        transactionRepository.save(Transaction.builder()
                .seller(seller)
                .amount(BigDecimal.valueOf(100))
                .paymentType(PaymentType.CASH)
                .transactionDate(LocalDateTime.now())
                .build());

        mockMvc.perform(get("/api/transactions/seller/" + seller.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].amount").value(100));
    }
}
