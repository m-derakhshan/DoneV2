package m.derakhshan.done.feature_task.domain.model

import androidx.annotation.Keep
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import m.derakhshan.done.ui.theme.*


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
}

class TaskStatusConverter {

    @TypeConverter
    fun fromInt(status: Int): TaskStatus {
        return when (status) {
            0 -> TaskStatus.Done
            1 -> TaskStatus.InProgress
            else -> throw IllegalArgumentException("Status not found")
        }
    }

    @TypeConverter
    fun toInt(status: TaskStatus): Int {
        return when (status) {
            is TaskStatus.Done -> 0
            is TaskStatus.InProgress -> 1
        }
    }
}

fun TaskModel.toTaskSyncModel(uid: String, action: TaskSyncAction): TaskSyncModel {
    return TaskSyncModel(
        userId = uid,
        id = this.id,
        description = this.description,
        color = this.color,
        date=this.date,
        time=this.time,
        status=this.status,
        action = action
    )
}