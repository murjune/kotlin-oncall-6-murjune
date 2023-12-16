package oncall.domain.schedule

import oncall.domain.date.DayOfWeek
import oncall.domain.date.MonthlyDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class EmergencyScheduleGeneratorTest {

    @Test
    fun `기능 테스트`() {
        // given
        val date = MonthlyDate(4, 1, DayOfWeek.of("토"))
        val weekendWorkers =
            "허브,쥬니,말랑,라온,헤나,우코,에단,수달,파워,히이로,마코,슬링키,모디,연어,깃짱,리오,고니,박스터,달리,조이,노아이즈,도이,도치,홍고,스캇,폴로,해시,로지,첵스,아이크,우가,푸만능,애쉬,로이스,오션".split(
                ","
            )
        val holidayWorkers =
            "오션,로이스,애쉬,푸만능,우가,아이크,첵스,로지,해시,폴로,스캇,홍고,도치,도이,노아이즈,조이,달리,박스터,고니,리오,깃짱,연어,모디,슬링키,마코,히이로,파워,수달,에단,우코,헤나,라온,말랑,쥬니,허브".split(
                ","
            )
        // when
        val res = EmergencyScheduleGenerator().generate(date, weekendWorkers, holidayWorkers)
        // then
        assertThat<List<String>>(res).isEqualTo(
            """
                4월 1일 토 오션
                4월 2일 일 로이스
                4월 3일 월 허브
                4월 4일 화 쥬니
                4월 5일 수 말랑
                4월 6일 목 라온
                4월 7일 금 헤나
                4월 8일 토 애쉬
                4월 9일 일 푸만능
                4월 10일 월 우코
                4월 11일 화 에단
                4월 12일 수 수달
                4월 13일 목 파워
                4월 14일 금 히이로
                4월 15일 토 우가
                4월 16일 일 아이크
                4월 17일 월 마코
                4월 18일 화 슬링키
                4월 19일 수 모디
                4월 20일 목 연어
                4월 21일 금 깃짱
                4월 22일 토 첵스
                4월 23일 일 로지
                4월 24일 월 리오
                4월 25일 화 고니
                4월 26일 수 박스터
                4월 27일 목 달리
                4월 28일 금 조이
                4월 29일 토 해시
                4월 30일 일 폴로
                """.trimIndent().split("\n")
        )
    }

    @Test
    fun `기능 테스트 2`() {
        // given
        val generator = EmergencyScheduleGenerator()
        val date = MonthlyDate(5, 1, DayOfWeek.of("월"))
        val weekDayWorkers = "준팍,도밥,고니,수아,루루,글로,솔로스타,우코,슬링키,참새,도리".split(",")
        val holidaysWorkers = "수아,루루,글로,솔로스타,우코,슬링키,참새,도리,준팍,도밥,고니".split(",")
        // when
        val result = generator.generate(date, weekDayWorkers, holidaysWorkers)
        // then
        assertThat(result).isEqualTo(
            """
            5월 1일 월 준팍
            5월 2일 화 도밥
            5월 3일 수 고니
            5월 4일 목 수아
            5월 5일 금(휴일) 루루
            5월 6일 토 수아
            5월 7일 일 글로
            5월 8일 월 루루
            5월 9일 화 글로
            5월 10일 수 솔로스타
            5월 11일 목 우코
            5월 12일 금 슬링키
            5월 13일 토 솔로스타
            5월 14일 일 우코
            5월 15일 월 참새
            5월 16일 화 도리
            5월 17일 수 준팍
            5월 18일 목 도밥
            5월 19일 금 고니
            5월 20일 토 슬링키
            5월 21일 일 참새
            5월 22일 월 수아
            5월 23일 화 루루
            5월 24일 수 글로
            5월 25일 목 솔로스타
            5월 26일 금 우코
            5월 27일 토 도리
            5월 28일 일 준팍
            5월 29일 월 슬링키
            5월 30일 화 참새
            5월 31일 수 도리
            """.trimIndent().split("\n")
        )
    }
}