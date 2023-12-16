package oncall.view

import camp.nextstep.edu.missionutils.Console

class InputView {
    fun readScheduleDate(input: String = Console.readLine()): List<String> {
        val date = input.split(COMMA).apply { validateDate() }
        return date
    }

    fun readWeekDayWorkers(input: String = Console.readLine()): List<String> {
        val workers = input.split(COMMA).apply { validateWorkers() }
        return workers
    }

    fun readHolidayWorkers(input: String = Console.readLine()): List<String> {
        val workers = input.split(COMMA).apply { validateWorkers() }
        return workers
    }

    private fun List<String>.validateDate() {
        require(size == 2) {
            "날짜는 월,요일로 입력해주세요."
        }
    }

    private fun List<String>.validateWorkers() {
        require(size in 5..35) {
            "인원은 5명 이상 35명 이하로 입력해주세요."
        }
        require(distinct() == this) {
            "중복된 이름이 있습니다."
        }
        require(all { it.length in 1..5 }) {
            "이름은 1자 이상 5자 이하로 입력해주세요."
        }
    }

    companion object {
        const val COMMA = ","
    }
}