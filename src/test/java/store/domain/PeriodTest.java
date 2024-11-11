package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PeriodTest {

    @CsvSource({
            "2020-01-02, false",
            "2020-01-03, true",
            "2020-01-04, true",
            "2020-01-05, true",
            "2020-01-06, false",
    })
    @ParameterizedTest
    void 기간_내에_속하는지_알_수_있다(LocalDate currDate, boolean expected) {
        //given
        LocalDate startDate = LocalDate.of(2020, 1, 3);
        LocalDate endDate = LocalDate.of(2020, 1, 5);
        Period period = new Period(startDate, endDate);

        //when
        boolean result = period.isWithin(currDate);

        //then
        assertThat(result).isEqualTo(expected);
    }
}