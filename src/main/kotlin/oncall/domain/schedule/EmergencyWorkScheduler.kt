package oncall.domain.schedule

import oncall.domain.date.MonthlyDate
import java.util.*

class EmergencyWorkScheduler() : WorkScheduler {

    override fun schedule(
        date: MonthlyDate,
        weekDayWorkers: List<String>,
        holidaysWorkers: List<String>
    ): List<String> {
        val workScheduler = mutableListOf<String>()
        val workSchedulerDate = mutableListOf<MonthlyDate>()
        val restHolidayWorkers = LinkedList<String>()
        val restWeekdayWorkers = LinkedList<String>()
        val holidaysWorkers = LinkedList(holidaysWorkers.extended())
        val weekDayWorkers = LinkedList(weekDayWorkers.extended())

        var currentDate = date
        while (true) {
            val previousWorker = workScheduler.lastOrNull()
            workSchedulerDate.add(currentDate)
            // 휴일 + 주말
            if (currentDate.isHoliday() || currentDate.isWeekend()) {
                val worker: String = if (restHolidayWorkers.isNotEmpty()) {
                    restHolidayWorkers.remove()
                } else {
                    // 이전 일한 사람이 없으면
                    if (previousWorker.isNullOrBlank()) {
                        holidaysWorkers.remove()
                    } else {
                        val tmp = holidaysWorkers.remove()
                        if (previousWorker == tmp) {
                            restHolidayWorkers.add(tmp)
                            holidaysWorkers.remove()
                        } else {
                            tmp
                        }
                    }
                }
                workScheduler.add(worker)
            }
            // 평일
            else {
                val worker: String = if (restWeekdayWorkers.isNotEmpty()) {
                    restWeekdayWorkers.remove()
                } else {
                    if (previousWorker.isNullOrBlank()) {
                        weekDayWorkers.remove()
                    } else {
                        val tmp = weekDayWorkers.remove()
                        if (previousWorker == tmp) {
                            restWeekdayWorkers.add(tmp)
                            weekDayWorkers.remove()
                        } else {
                            tmp
                        }
                    }
                }
                workScheduler.add(worker)
            }
            if (currentDate.hasNext().not()) break
            currentDate = currentDate.next()
        }
        return workScheduler.mapIndexed { i, worker -> "${workSchedulerDate[i]} $worker" }
    }

    private fun List<String>.extended(): List<String> {
        val timesToRepeat = (MAX_DAYS_IN_MONTH / size) + 1
        return (1..timesToRepeat).flatMap { this }
    }

    companion object {
        private const val MAX_DAYS_IN_MONTH = 31

    }
}