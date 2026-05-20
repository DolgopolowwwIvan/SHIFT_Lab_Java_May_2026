package shift.lab.shift_lab_java_may_2026.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import shift.lab.shift_lab_java_may_2026.service.util.PeriodCalculator;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PeriodCalculatorTest {

    private final PeriodCalculator periodCalculator = new PeriodCalculator();

    @Test
    void calculateStart_shouldReturnStartOfProvidedDate_whenDateProvided() {
        LocalDate date = LocalDate.of(2026, 6, 15);

        LocalDateTime result = periodCalculator.calculateStart(date, "month");

        assertThat(result).isEqualTo(LocalDateTime.of(2026, 6, 15, 0, 0));
    }

    @ParameterizedTest
    @CsvSource({
            "day",
            "DAY",
            "Day"
    })
    void calculateStart_shouldReturnTodayStart_whenPeriodIsDay(String period) {
        LocalDate today = LocalDate.now();

        LocalDateTime result = periodCalculator.calculateStart(null, period);

        assertThat(result).isEqualTo(today.atStartOfDay());
    }

    @Test
    void calculateStart_shouldReturnMonthStart_whenPeriodIsMonth() {
        LocalDateTime result = periodCalculator.calculateStart(null, "month");

        assertThat(result.getDayOfMonth()).isEqualTo(1);
        assertThat(result.getHour()).isEqualTo(0);
        assertThat(result.getMinute()).isEqualTo(0);
    }

    @Test
    void calculateStart_shouldReturnQuarterStart_whenPeriodIsQuarter() {
        LocalDateTime result = periodCalculator.calculateStart(null, "quarter");

        int month = result.getMonthValue();
        assertThat(month).isIn(1, 4, 7, 10);
        assertThat(result.getDayOfMonth()).isEqualTo(1);
    }

    @Test
    void calculateStart_shouldReturnYearStart_whenPeriodIsYear() {
        LocalDateTime result = periodCalculator.calculateStart(null, "year");

        assertThat(result.getMonthValue()).isEqualTo(1);
        assertThat(result.getDayOfMonth()).isEqualTo(1);
        assertThat(result.getHour()).isEqualTo(0);
    }

    @Test
    void calculateStart_shouldThrowIllegalArgumentException_whenPeriodIsInvalid() {
        assertThatThrownBy(() -> periodCalculator.calculateStart(null, "invalid"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid period");
    }

    @NullSource
    @ParameterizedTest
    void calculateStart_shouldReturnTodayStart_whenPeriodIsNull(String period) {
        LocalDate today = LocalDate.now();
        LocalDateTime result = periodCalculator.calculateStart(null, period);

        assertThat(result).isEqualTo(today.atStartOfDay());
    }
}
