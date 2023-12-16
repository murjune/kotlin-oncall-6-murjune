package oncall

import oncall.controller.MonthlyWorkScheduleController
import oncall.controller.error.InputErrorHandler

fun main() {
    MonthlyWorkScheduleController(InputErrorHandler()).start()
}