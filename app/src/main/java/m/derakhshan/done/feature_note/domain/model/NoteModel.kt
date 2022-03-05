package m.derakhshan.done.feature_note.domain.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import m.derakhshan.done.ui.theme.*


@Keep
@Entity
data class NoteModel(
    @PrimaryKey
    val id: Int,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val colors = listOf(
            LightGreen, LightBlue, LightPurple,
            LightOrange, LightRed, LightYellow
        )
    }
}


fun NoteModel.toNoteSyncModel(uid: String, action: NoteSyncAction): NoteSyncModel {
    return NoteSyncModel(
        userId = uid,
        noteId = this.id,
        title = this.title,
        content = this.content,
        color = this.color,
        timestamp = this.timestamp,
        action = action
    )
}


class InvalidNoteException(message: String) : Exception(message)
