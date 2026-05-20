package shift.lab.shift_lab_java_may_2026.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import shift.lab.shift_lab_java_may_2026.exception.EntityNotFoundException;
import shift.lab.shift_lab_java_may_2026.service.TransactionService;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    private static final String BASE_URL = "/api/transactions";

    @Test
    void getTransactionById_shouldReturnNotFound_whenTransactionDoesNotExist() throws Exception {
        when(transactionService.findById(999))
                .thenThrow(new EntityNotFoundException("Not found Transaction by id = 999"));

        mockMvc.perform(get(BASE_URL + "/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Entity not found"));
    }

    @Test
    void createTransaction_shouldReturnBadRequest_whenAmountIsNegative() throws Exception {
        var request = """
                {
                    "sellerId": 1,
                    "amount": -100,
                    "paymentType": "CASH",
                    "transactionDate": "2025-06-15T10:30:00"
                }
                """;

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTransaction_shouldReturnNotFound_whenTransactionDoesNotExist() throws Exception {
        doThrow(new EntityNotFoundException("Not found Transaction by id = 999"))
                .when(transactionService).delete(999);

        mockMvc.perform(delete(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

}
