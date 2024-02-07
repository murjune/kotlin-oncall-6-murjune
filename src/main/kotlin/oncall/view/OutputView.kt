package oncall.view

class OutputView {
    fun writeDateGuide() {
        print("비상 근무를 배정할 월과 시작 요일을 입력하세요> ")
    }

    fun writeWeekDayWorkersGuide() {
        print("평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ")
    }

    fun writeHolidayWorkersGuide() {
        print("휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ")
    }

    fun writeMonthlyWorkScheduleResult(scheduleResult: List<String>) {
        println(" ")
        scheduleResult.forEach { println(it) }
    }

    fun writeError(message: String) {
        println(message)
    }
}