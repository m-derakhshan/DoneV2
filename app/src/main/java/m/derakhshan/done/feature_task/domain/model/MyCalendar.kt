package m.derakhshan.done.feature_task.domain.model

import java.util.*


class MyCalendar(nextMonth: Int = 0) {

    private val calendar = Calendar.getInstance().apply {
        this.add(Calendar.MONTH, nextMonth)
    }
    private val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    private val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    private val originalDayOfWeek = dayOfMonth % 7

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)

    private fun getDaysOfMonthCount(month: Int): Int {
        return when (month) {
            0 -> 31
            1 -> if (leapYearChecker()) 29 else 28
            2 -> 31
            3 -> 30
            4 -> 31
            5 -> 30
            6 -> 31
            7 -> 31
            8 -> 30
            9 -> 31
            10 -> 30
            else -> 31
        }
    }
    private fun leapYearChecker(): Boolean {
        val year = 1900
        return if (year % 4 == 0) {
            if (year % 100 == 0) {
                year % 400 == 0
            } else
                true
        } else
            false
    }

    fun getMonthName(month: Int): String {
        return when (month) {
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            else -> "December"
        }
    }
    fun generateDays(): List<TaskDate> {

        val inactiveDays =
            when {
                originalDayOfWeek < dayOfWeek -> (dayOfWeek - dayOfMonth % 7)
                originalDayOfWeek == dayOfWeek -> 0
                else -> {
                    var space = 0
                    for (i in 1..7)
                        if ((i + originalDayOfWeek) % 7 == dayOfWeek)
                            space = i
                    space
                }
            }
        val result = ArrayList<TaskDate>()
        for (i in 1..inactiveDays)
            result.add(
                TaskDate(
                    day = -1,
                    month = month,
                    year = year
                )
            )
        for (i in 1..getDaysOfMonthCount(month))
            result.add(
                TaskDate(
                    day = i,
                    month = month,
                    year = year
                )
            )
        return result
    }

}