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
    val month = calendar.get(Calendar.MONTH)+1

    private fun getDaysOfMonthCount(month: Int): Int {
        return when (month) {
            1 -> 31
            2 -> if (leapYearChecker()) 29 else 28
            3 -> 31
            4 -> 30
            5 -> 31
            6 -> 30
            7 -> 31
            8 -> 31
            9 -> 30
            10 -> 31
            11 -> 30
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
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
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