package oncall.domain.date

data class MonthlyDate(private val month: Int, private val dayOfMonth: Int, private val dayOfWeek: DayOfWeek) {

    private val limitDayOfMonth = cachedDay[month] ?: throw IllegalArgumentException(MONTH_RANGE_ERROR_MESSAGE)

    init {
        require(month in MONTH_RANGE) { MONTH_RANGE_ERROR_MESSAGE }
    }

    // 다음날 주기
    fun next(): MonthlyDate {
        require(hasNext()) { "${limitDayOfMonth}일이 넘어갔습니다" }
        return MonthlyDate(month, dayOfMonth + 1, dayOfWeek.next())
    }

    // 다음날이 있니?
    fun hasNext(): Boolean = (dayOfMonth + 1 <= limitDayOfMonth)

    override fun toString(): String {
        return "${month}월 ${dayOfMonth}일 ${dayOfWeek}${if (isHoliday()) "(휴일)" else ""}"
    }

    // 휴일이니?
    fun isHoliday(): Boolean {
        return cachedHolidays[month] == dayOfMonth
    }

    fun isWeekend(): Boolean {
        return dayOfWeek.isWeekend()
    }

    // 1/1, 3/1, 5/5, 6/6, 8/15, 10/3, 10/9, 12/25 + (토, 일)
    companion object {
        private const val MONTH_RANGE_ERROR_MESSAGE = "month 는 1월부터 12월 사이값이여야 합니다"
        private val MONTH_RANGE = 1..12
        private val cachedDay = mapOf(
            1 to 31, 2 to 28, 3 to 31, 4 to 30, 5 to 31, 6 to 30,
            7 to 31, 8 to 31, 9 to 30, 10 to 31, 11 to 30, 12 to 31
        )
        private val cachedHolidays = mapOf(
            1 to 1, 3 to 1, 5 to 5, 6 to 6, 8 to 15, 10 to 3, 10 to 9, 12 to 25
        )
    }
}