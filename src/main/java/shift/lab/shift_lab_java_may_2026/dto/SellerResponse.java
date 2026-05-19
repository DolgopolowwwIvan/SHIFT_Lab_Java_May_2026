package shift.lab.shift_lab_java_may_2026.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerResponse {

    private Integer id;
    private String name;
    private String contactInfo;
    private LocalDateTime registrationDate;
}