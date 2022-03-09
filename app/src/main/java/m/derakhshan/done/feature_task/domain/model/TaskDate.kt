package m.derakhshan.done.feature_task.domain.model

import java.util.*

data class TaskDate(
    val day: Int,
    val month: Int,
    val year: Int
) {
    companion object {
        val today: TaskDate
            get() {
                val calendar = Calendar.getInstance()
                return TaskDate(
                    day = calendar.get(Calendar.DAY_OF_MONTH),
                    month = calendar.get(Calendar.MONTH),
                    year = calendar.get(Calendar.YEAR)
                )
            }
    }
}


