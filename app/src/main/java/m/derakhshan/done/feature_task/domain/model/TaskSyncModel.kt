package m.derakhshan.done.feature_task.domain.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter


sealed class TaskSyncAction {
    object Insert : TaskSyncAction()
    object Update : TaskSyncAction()
    object Delete : TaskSyncAction()
}


@Keep
@Entity
data class TaskSyncModel(
    val userId: String,
    @PrimaryKey
    val id: Long,
    val description: String,
    val color: Int,
    val date: String,
    val time: String,
    val status: TaskStatus = TaskStatus.InProgress,
    val action: TaskSyncAction
)

fun TaskSyncModel.toTaskModel(): TaskModel {
    return TaskModel(
        id = this.id,
        description = this.description,
        color = this.color,
        date = this.date,
        time = this.time,
        status = this.status
    )
}


class TaskSyncActionConverter {
    @TypeConverter
    fun fromActionString(value: String): TaskSyncAction {
        return when (value) {
            "Insert" -> TaskSyncAction.Insert
            "Update" -> TaskSyncAction.Update
            else -> TaskSyncAction.Delete
        }
    }

    @TypeConverter
    fun toActionString(action: TaskSyncAction): String {
        return when (action) {
            is TaskSyncAction.Insert -> "Insert"
            is TaskSyncAction.Update -> "Update"
            else -> "Delete"
        }
    }
}