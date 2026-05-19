package shift.lab.shift_lab_java_may_2026.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Contact info is required")
    private String contactInfo;
}
