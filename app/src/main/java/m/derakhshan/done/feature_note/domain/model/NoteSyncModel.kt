package m.derakhshan.done.feature_note.domain.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter


sealed class NoteSyncAction {
    object Insert : NoteSyncAction()
    object Update : NoteSyncAction()
    object Delete : NoteSyncAction()
}


@Keep
@Entity
data class NoteSyncModel(
    val userId: String,
    @PrimaryKey
    val noteId: Int,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val action: NoteSyncAction
)

fun NoteSyncModel.toNoteModel(): NoteModel {
    return NoteModel(
        id = this.noteId,
        title = this.title,
        content = this.content,
        timestamp = this.timestamp,
        color = this.color
    )
}


class NoteSyncActionConverter {
    @TypeConverter
    fun fromActionString(value: String): NoteSyncAction {
        return when (value) {
            "Insert" -> NoteSyncAction.Insert
            "Update" -> NoteSyncAction.Update
            else -> NoteSyncAction.Delete
        }
    }

    @TypeConverter
    fun toActionString(action: NoteSyncAction): String {
        return when (action) {
            is NoteSyncAction.Insert -> "Insert"
            is NoteSyncAction.Update -> "Update"
            else -> "Delete"
        }
    }
}