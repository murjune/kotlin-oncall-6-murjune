package oncall.domain.date

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class DayOfWeekTest {

    @Test
    fun `다음 요일을 알 수 있다`() {
        // given
        val today = DayOfWeek.MON
        val today2 = DayOfWeek.SUN
        // when
        val nextDay = today.next()
        val nextDay2 = today2.next()
        // then
        assertThat(nextDay).isEqualTo(DayOfWeek.TUE)
        assertThat(nextDay2).isEqualTo(DayOfWeek.MON)
    }

    @Test
    fun `오늘이 주말인지 확인할 수 있다`() {
        // given
        val today = DayOfWeek.SAT
        val today2 = DayOfWeek.MON
        // when
        val isWeekend = today.isWeekend()
        val isWeekend2 = today2.isWeekend()
        // then
        assertThat(isWeekend).isTrue()
        assertThat(isWeekend2).isFalse()
    }

    @Test
    fun `문자열 요일로 DayOfWeek를 생성할 수 있다`() {
        // given
        val day = "월"
        // when
        val dayOfWeek = DayOfWeek.of(day)
        // then
        assertThat(dayOfWeek).isEqualTo(DayOfWeek.MON)
    }

    @Test
    fun `월 ~ 금이 아닌 문자열로 DayOfWeek 를 생성 할 수 없다`() {
        assertThrows<IllegalArgumentException>("day 는 월, 화, 수, 목, 금, 토, 일 중 하나여야 합니다") {
            DayOfWeek.of("월요일")
        }
    }
}