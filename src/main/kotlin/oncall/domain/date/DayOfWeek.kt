package oncall.domain.date

enum class DayOfWeek(private val label: String) {
    MON("월"), TUE("화"), WED("수"),
    THUR("목"), FRI("금"), SAT("토"), SUN("일");

    fun next(): DayOfWeek {
        return when (this) {
            MON -> TUE
            TUE -> WED
            WED -> THUR
            THUR -> FRI
            FRI -> SAT
            SAT -> SUN
            SUN -> MON
        }
    }

    fun isWeekend() = (this == SAT || this == SUN)

    override fun toString() = label

    companion object {
        private val cachedDays = entries.associateBy { it.label }

        @JvmStatic
        fun of(day: String): DayOfWeek {
            return cachedDays[day]
                ?: throw IllegalArgumentException(
                    "day 는 월, 화, 수, 목, 금, 토, 일 중 하나여야 합니다"
                )
        }
    }
}