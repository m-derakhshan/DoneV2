package m.derakhshan.done.feature_task.domain.model

import androidx.annotation.Keep
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import m.derakhshan.done.ui.theme.*
import java.lang.IllegalArgumentException
import java.sql.Timestamp


@Entity
@Keep
data class TaskModel(
    @PrimaryKey
    val id: Long = System.currentTimeMillis(),
    val description: String,
    val color: Int,
    val date: String,
    val time: String,
    val status: TaskStatus = TaskStatus.InProgress
) {
    companion object {
        val colors = listOf(
            LightGreen, LightBlue, LightPurple,
            LightOrange, LightRed, LightYellow
        )
    }
}

fun TaskModel.toDarkColor():Color{
    return when (this.color) {
        LightGreen.toArgb() -> DarkGreen
        LightBlue.toArgb() -> DarkBlue
        LightPurple.toArgb() -> DarkPurple
        LightOrange.toArgb() -> DarkOrange
        LightRed.toArgb() -> DarkRed
        else -> DarkYellow
    }
}

sealed class TaskStatus(val status:String) {
    object Done : TaskStatus("Done")
    object InProgress : TaskStatus("InProgress")
    object Postpone : TaskStatus("Postpone")
}

class TaskStatusConverter {

    @TypeConverter
    fun fromInt(status: Int): TaskStatus {
        return when (status) {
            0 -> TaskStatus.Done
            1 -> TaskStatus.Postpone
            2 -> TaskStatus.InProgress
            else -> throw IllegalArgumentException("Status not found")
        }
    }

    @TypeConverter
    fun toInt(status: TaskStatus): Int {
        return when (status) {
            is TaskStatus.Done -> 0
            TaskStatus.Postpone -> 1
            is TaskStatus.InProgress -> 2
        }
    }
}