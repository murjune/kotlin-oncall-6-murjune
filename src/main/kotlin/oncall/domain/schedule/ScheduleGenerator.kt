package oncall.domain.schedule

import oncall.domain.date.MonthlyDate

fun interface ScheduleGenerator {
    fun generate(date: MonthlyDate, weekDayWorkers: List<String>, holidaysWorkers: List<String>): List<String>
}