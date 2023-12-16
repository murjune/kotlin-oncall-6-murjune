package oncall.domain.schedule

import oncall.domain.date.MonthlyDate
import java.util.*

class EmergencyWorkScheduler() : WorkScheduler {

    override fun schedule(
        date: MonthlyDate, weekDayWorkers: List<String>, holidaysWorkers: List<String>
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
            val worker = selectWorkerByDate(
                currentDate, previousWorker, restWeekdayWorkers, weekDayWorkers, restHolidayWorkers, holidaysWorkers
            )
            workScheduler.add(worker)
            if (currentDate.hasNext().not()) break
            currentDate = currentDate.next()
        }

        return workScheduler.mapIndexed { i, worker -> "${workSchedulerDate[i]} $worker" }
    }

    private fun selectWorkerByDate(
        date: MonthlyDate,
        previousWorker: String?,
        restWeekdayWorkers: LinkedList<String>,
        weekDayWorkers: LinkedList<String>,
        restHolidayWorkers: LinkedList<String>,
        holidaysWorkers: LinkedList<String>,
    ): String {
        return if (date.isHoliday() || date.isWeekend()) {
            selectWorker(previousWorker, restHolidayWorkers, holidaysWorkers)
        } else {
            selectWorker(previousWorker, restWeekdayWorkers, weekDayWorkers)
        }
    }

    private fun selectWorker(
        previousWorker: String?, restWorkers: LinkedList<String>, workers: LinkedList<String>
    ): String {
        return if (restWorkers.isNotEmpty()) {
            restWorkers.remove()
        } else {
            val currentWorker = workers.remove()
            if (previousWorker == currentWorker) {
                restWorkers.add(currentWorker)
                workers.remove()
            } else {
                currentWorker
            }
        }
    }

    private fun List<String>.extended(): List<String> {
        val timesToRepeat = (MAX_DAYS_IN_MONTH / size) + 1
        return (1..timesToRepeat).flatMap { this }
    }

    companion object {
        private const val MAX_DAYS_IN_MONTH = 31

    }
}