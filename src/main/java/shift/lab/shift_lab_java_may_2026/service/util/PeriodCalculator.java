package shift.lab.shift_lab_java_may_2026.service.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class PeriodCalculator {

    public LocalDateTime calculateStart(LocalDate date, String period) {
        if (date != null) {
            return date.atStartOfDay();
        }
        if (period != null) {
            return switch (period.toLowerCase()) {
                case "day" -> LocalDate.now().atStartOfDay();
                case "month" -> LocalDate.now().withDayOfMonth(1).atStartOfDay();
                case "quarter" -> {
                    int month = LocalDate.now().getMonthValue();
                    int quarterStartMonth = ((month - 1) / 3) * 3 + 1;
                    yield LocalDate.now().withMonth(quarterStartMonth).withDayOfMonth(1).atStartOfDay();
                }
                case "year" -> LocalDate.now().withDayOfYear(1).atStartOfDay();
                default -> throw new IllegalArgumentException("Invalid period: " + period);
            };
        }
        return LocalDate.now().atStartOfDay();
    }
}