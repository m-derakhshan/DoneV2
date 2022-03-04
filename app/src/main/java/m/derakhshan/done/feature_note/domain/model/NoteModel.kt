package m.derakhshan.done.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import m.derakhshan.done.ui.theme.*


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


class InvalidNoteException(message: String) : Exception(message)
