package shift.lab.shift_lab_java_may_2026.mapper;

import shift.lab.shift_lab_java_may_2026.dto.SellerRequest;
import shift.lab.shift_lab_java_may_2026.dto.SellerResponse;
import shift.lab.shift_lab_java_may_2026.model.Seller;

public class SellerMapper {

    public static SellerResponse toResponse(
            Seller seller
    ) {
        return SellerResponse.builder()
                .id(seller.getId())
                .name(seller.getName())
                .contactInfo(seller.getContactInfo())
                .registrationDate(seller.getRegistrationDate())
                .build();
    }

    public static Seller toEntity(
            SellerRequest request
    ) {
        return Seller.builder()
                .name(request.getName())
                .contactInfo(request.getContactInfo())
                .build();
    }
}