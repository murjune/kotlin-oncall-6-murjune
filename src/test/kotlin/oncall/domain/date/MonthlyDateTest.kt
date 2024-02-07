package oncall.domain.date

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class MonthlyDateTest {

    @Test
    fun `해당 월의 다음날을 알 수 있다`() {
        // given
        val monthlyDate = MonthlyDate(1, 1, DayOfWeek.of("월"))
        val expectedDate = MonthlyDate(1, 2, DayOfWeek.of("화"))
        // when
        val nextDay = monthlyDate.next()
        // then
        assertThat(nextDay).isEqualTo(expectedDate)
    }

    @Test
    fun `해당 월의 마지막 날짜에서는 다음날을 알 수 없다`() {
        assertThrows<IllegalArgumentException>("31일이 넘어갔습니다") {
            MonthlyDate(1, 31, DayOfWeek.of("월")).next()
        }
    }
    @Test
    fun `다음 month가 있는지 확인 할 수 있음`() {
        // given
        val monthlyDate = MonthlyDate(1, 1, DayOfWeek.of("월"))
        val monthlyDate2 = MonthlyDate(1, 31, DayOfWeek.of("월"))

        // then
        assertThat(monthlyDate.hasNext()).isTrue()
        assertThat(monthlyDate2.hasNext()).isFalse()
    }

    @Test
    fun `month는 1 ~ 12일 사이여야 한다`() {
        assertThrows<IllegalArgumentException>("month 는 1월부터 12월 사이값이여야 합니다") {
            MonthlyDate(0, 1, DayOfWeek.of("월"))
        }
    }

    @Test
    fun `오늘이 휴일인지 알 수 있다`() {
        val monthlyDate = MonthlyDate(1, 1, DayOfWeek.of("월"))
        assertThat(monthlyDate.isHoliday()).isTrue()
    }

    @Test
    fun `오늘이 주말인지 알 수 있다`() {
        val monthlyDate = MonthlyDate(1, 1, DayOfWeek.of("일"))
        assertThat(monthlyDate.isWeekend()).isTrue()
    }
}