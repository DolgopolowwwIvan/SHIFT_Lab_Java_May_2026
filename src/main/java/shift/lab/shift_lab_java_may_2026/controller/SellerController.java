package shift.lab.shift_lab_java_may_2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shift.lab.shift_lab_java_may_2026.dto.SellerRequest;
import shift.lab.shift_lab_java_may_2026.dto.SellerResponse;
import shift.lab.shift_lab_java_may_2026.mapper.SellerMapper;
import shift.lab.shift_lab_java_may_2026.service.SellerService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<SellerResponse>> getAllSellers() {
        return ResponseEntity.ok(
                sellerService.findAll().stream()
                        .map(SellerMapper::toResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponse> getSellerById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(
                SellerMapper.toResponse(sellerService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<SellerResponse> createSeller(
            @RequestBody @Valid SellerRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SellerMapper.toResponse(
                        sellerService.create(SellerMapper.toEntity(request))
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerResponse> updateSeller(
            @PathVariable("id") Integer id,
            @RequestBody @Valid SellerRequest request
    ) {
        return ResponseEntity.ok(
                SellerMapper.toResponse(
                        sellerService.update(id, SellerMapper.toEntity(request))
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(
            @PathVariable("id") Integer id
    ) {
        sellerService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/analytics/top")
    public ResponseEntity<SellerResponse> getTopSeller(
            @RequestParam(name = "date", required = false) LocalDate date,
            @RequestParam(name = "period", required = false) String period
    ) {
        return ResponseEntity.ok(
                SellerMapper.toResponse(sellerService.findTopSellerByPeriod(date, period))
        );
    }

    @GetMapping("/analytics/low-performers")
    public ResponseEntity<List<SellerResponse>> getLowPerformers(
            @RequestParam(name = "threshold") BigDecimal threshold,
            @RequestParam(name = "date", required = false) LocalDate date,
            @RequestParam(name = "period", required = false) String period
    ) {
        return ResponseEntity.ok(
                sellerService.findSellersWithTotalAmountLessThan(threshold, date, period)
                        .stream()
                        .map(SellerMapper::toResponse)
                        .toList()
        );
    }
}