package shift.lab.shift_lab_java_may_2026.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import shift.lab.shift_lab_java_may_2026.model.Seller;
import shift.lab.shift_lab_java_may_2026.repository.SellerRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class SellerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SellerRepository sellerRepository;

    @Test
    void getAllSellers_shouldReturnAllSavedSellers() throws Exception {
        sellerRepository.save(Seller.builder()
                .name("Seller A")
                .contactInfo("a@example.com")
                .registrationDate(LocalDateTime.now())
                .build());
        sellerRepository.save(Seller.builder()
                .name("Seller B")
                .contactInfo("b@example.com")
                .registrationDate(LocalDateTime.now())
                .build());

        mockMvc.perform(get("/api/sellers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Seller A"));
    }

    @Test
    void createSeller_shouldSaveSellerAndReturnCreated() throws Exception {
        var request = """
                {
                    "name": "New Seller",
                    "contactInfo": "new@example.com"
                }
                """;

        mockMvc.perform(post("/api/sellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Seller"));

        List<Seller> sellers = sellerRepository.findAll();
        assertThat(sellers).hasSize(1);
        assertThat(sellers.getFirst().getName()).isEqualTo("New Seller");
    }

    @Test
    void deleteSeller_shouldRemoveSellerFromDatabase() throws Exception {
        Seller seller = sellerRepository.save(Seller.builder()
                .name("To Delete")
                .contactInfo("delete@example.com")
                .registrationDate(LocalDateTime.now())
                .build());

        mockMvc.perform(delete("/api/sellers/" + seller.getId()))
                .andExpect(status().isOk());

        assertThat(sellerRepository.findById(seller.getId())).isEmpty();
    }
}
