package shift.lab.shift_lab_java_may_2026.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import shift.lab.shift_lab_java_may_2026.exception.EntityNotFoundException;
import shift.lab.shift_lab_java_may_2026.service.SellerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SellerController.class)
class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SellerService sellerService;

    private static final String BASE_URL = "/api/sellers";

    @Test
    void getSellerById_shouldReturnNotFound_whenSellerDoesNotExist() throws Exception {
        when(sellerService.findById(999))
                .thenThrow(new EntityNotFoundException("Not found Seller by id = 999"));

        mockMvc.perform(get(BASE_URL + "/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Entity not found"));
    }

    @Test
    void createSeller_shouldReturnBadRequest_whenNameIsBlank() throws Exception {
        var request = """
                {
                    "name": "",
                    "contactInfo": "new@example.com"
                }
                """;

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteSeller_shouldReturnNotFound_whenSellerDoesNotExist() throws Exception {
        doThrow(new EntityNotFoundException("Not found Seller by id = 999"))
                .when(sellerService).delete(999);

        mockMvc.perform(delete(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTopSeller_shouldReturnNotFound_whenNoSellerFound() throws Exception {
        when(sellerService.findTopSellerByPeriod(any(), any()))
                .thenThrow(new EntityNotFoundException("No top seller found"));

        mockMvc.perform(get(BASE_URL + "/analytics/top")
                        .param("period", "day"))
                .andExpect(status().isNotFound());
    }

}
