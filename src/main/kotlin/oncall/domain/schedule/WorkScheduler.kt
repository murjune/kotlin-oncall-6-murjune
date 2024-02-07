package oncall.domain.schedule

import oncall.domain.date.MonthlyDate

fun interface WorkScheduler {
    fun schedule(date: MonthlyDate, weekDayWorkers: List<String>, holidaysWorkers: List<String>): List<String>
}