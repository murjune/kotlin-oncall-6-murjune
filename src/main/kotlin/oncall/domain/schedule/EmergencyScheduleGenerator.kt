package oncall.domain.schedule

import oncall.domain.date.MonthlyDate
import java.util.*

class EmergencyScheduleGenerator() : ScheduleGenerator {

    override fun generate(
        date: MonthlyDate,
        weekDayWorkers: List<String>,
        holidaysWorkers: List<String>
    ): List<String> {
        val result = mutableListOf<String>()
        val resultDate = mutableListOf<MonthlyDate>()
        val restHolidayWorkers = LinkedList<String>()
        val restWeekdayWorkers = LinkedList<String>()
        val holidaysWorkers = LinkedList(holidaysWorkers.multiply())
        val weekDayWorkers = LinkedList(weekDayWorkers.multiply())
        // 해당 월 말일(e. 31) 까지
        var curDate = date
        while (true) {
            val pre = result.lastOrNull()
            resultDate.add(curDate)
            if (curDate.isHoliday() || curDate.isWeekend()) {
                val worker: String = if (restHolidayWorkers.isNotEmpty()) {
                    restHolidayWorkers.remove()
                } else {
                    if (pre.isNullOrBlank()) {
                        holidaysWorkers.remove()
                    } else {
                        val tmp = holidaysWorkers.remove()
                        if (pre == tmp) {
                            restHolidayWorkers.add(tmp)
                            holidaysWorkers.remove()
                        } else {
                            tmp
                        }
                    }
                }
                result.add(worker)
            } else {
                val worker: String = if (restWeekdayWorkers.isNotEmpty()) {
                    restWeekdayWorkers.remove() // 수아
                } else {
                    if (pre.isNullOrBlank()) {
                        weekDayWorkers.remove()
                    } else {// pre : 루루
                        val tmp = weekDayWorkers.remove() // 글로
                        if (pre == tmp) {
                            restWeekdayWorkers.add(tmp) // rest : [수아]
                            weekDayWorkers.remove() // 루루
                        } else {
                            tmp
                        }
                    }
                }
                result.add(worker)
            }
            if (curDate.hasNext().not()) break
            curDate = curDate.next()
        }
        return result.mapIndexed { i, worker -> "${resultDate[i]} $worker" }
    }

    private fun List<String>.multiply(): List<String> {
        val mul = (31 / size) + 1
        return (1..mul).flatMap { this }
    }
}