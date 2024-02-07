package oncall.domain.schedule

import oncall.domain.date.MonthlyDate
import java.util.*

class EmergencyWorkScheduler() : WorkScheduler {
    private lateinit var workScheduler: MutableList<String>
    private lateinit var workSchedulerDate: MutableList<MonthlyDate>
    private lateinit var restHolidayWorkers: LinkedList<String>
    private lateinit var restWeekdayWorkers: LinkedList<String>
    private lateinit var holidaysWorkers: LinkedList<String>
    private lateinit var weekDayWorkers: LinkedList<String>

    override fun schedule(
        date: MonthlyDate, weekDayWorkers: List<String>, holidaysWorkers: List<String>
    ): List<String> {
        workScheduler = mutableListOf<String>()
        workSchedulerDate = mutableListOf<MonthlyDate>()
        restHolidayWorkers = LinkedList<String>()
        restWeekdayWorkers = LinkedList<String>()
        this.holidaysWorkers = LinkedList(holidaysWorkers.extended())
        this.weekDayWorkers = LinkedList(weekDayWorkers.extended())

        schedule(date)

        return workScheduler.mapIndexed { i, worker -> "${workSchedulerDate[i]} $worker" }
    }

    private fun schedule(date: MonthlyDate) {
        var currentDate = date
        while (true) {
            val previousWorker = workScheduler.lastOrNull()
            workSchedulerDate.add(currentDate)
            val worker = selectWorkerByDate(
                currentDate, previousWorker
            )
            workScheduler.add(worker)
            if (currentDate.hasNext().not()) break
            currentDate = currentDate.next()
        }
    }

    private fun selectWorkerByDate(
        date: MonthlyDate,
        previousWorker: String?,
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