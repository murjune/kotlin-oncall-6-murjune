package oncall.controller

import oncall.controller.error.ErrorHandler
import oncall.domain.date.MonthlyDate
import oncall.domain.schedule.EmergencyScheduleGenerator
import oncall.domain.schedule.ScheduleGenerator
import oncall.view.InputView
import oncall.view.OutputView

class MonthlyWorkScheduleController(
    private val errorHandler: ErrorHandler
) {
    private val outputView = OutputView()
    private val inputView = InputView()
    private val scheduleGenerator: ScheduleGenerator = EmergencyScheduleGenerator()
    private lateinit var today: MonthlyDate
    private lateinit var weekdayWorkers: List<String>
    private lateinit var holidayWorkers: List<String>
    fun start() {
        showDateView()
        showWorkersView()
        showScheduleResult()
    }

    private fun showDateView() {
        errorHandler.handle(
            action = {
                outputView.writeDateGuide()
                val (month, dayOfWeek) = inputView.readScheduleDate()
                today = MonthlyDate(month, dayOfWeek)
            },
            callback = {
                showDateView()
            },
            write = { outputView.writeError(it) },
        )
    }

    private fun showWorkersView() {
        errorHandler.handle(
            action = {
                outputView.writeWeekDayWorkersGuide()
                weekdayWorkers = inputView.readWeekDayWorkers()
                outputView.writeHolidayWorkersGuide()
                holidayWorkers = inputView.readHolidayWorkers()
                validateWorkers(weekdayWorkers, holidayWorkers)
            },
            callback = {
                showWorkersView()
            },
            write = { outputView.writeError(it) },
        )
    }

    private fun showScheduleResult() {
        val result = scheduleGenerator.generate(today, weekdayWorkers, holidayWorkers)
        outputView.writeMonthlyWorkScheduleResult(result)
    }

    private fun validateWorkers(weekdayWorkers: List<String>, holidayWorkers: List<String>) {
        require(weekdayWorkers.sorted() == holidayWorkers.sorted()) {
            "주중 근무자와 휴일 근무자가 일치하지 않습니다."
        }
    }
}
//
//fun main() {
//    val controller = MonthlyWorkScheduleController(InputErrorHandler())
//    controller.start()
//}