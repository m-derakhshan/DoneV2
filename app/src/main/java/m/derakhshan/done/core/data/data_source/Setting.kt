package m.derakhshan.done.core.data.data_source

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import m.derakhshan.done.feature_note.domain.model.NoteOrderSortType
import m.derakhshan.done.feature_note.domain.model.NoteOrderType
import javax.inject.Inject

class Setting @Inject constructor(
    @ApplicationContext context: Context
) {

    private val share = context.getSharedPreferences("share", Context.MODE_PRIVATE)
    private val edit = share.edit()

    var isUserLoggedIn: Boolean
        set(value) {
            edit.putBoolean("isUserLoggedIn", value)
            edit.apply()
        }
        get() = share.getBoolean("isUserLoggedIn", false)

    var lastNoteId: Int
        set(value) {
            edit.putInt("lastNoteId", value)
            edit.apply()
        }
        get() {
            val id = share.getInt("lastNoteId", 0)
            edit.putInt("lastNoteId", id + 1)
            edit.apply()
            return id
        }

    var noteOrderSortType: NoteOrderSortType
        set(value) {
            edit.putString(
                "noteOrderSortType", when (value) {
                    is NoteOrderSortType.Ascending -> "Ascending"
                    is NoteOrderSortType.Descending -> "Descending"
                }
            )
            edit.apply()
        }
        get() {
            val type = share.getString("noteOrderSortType", "Ascending")
            return if (type == "Ascending")
                NoteOrderSortType.Ascending
            else NoteOrderSortType.Descending
        }

    var noteOrderType: NoteOrderType
        set(value) {
            edit.putString(
                "NoteOrderType", when (value) {
                    is NoteOrderType.Color -> "Color"
                    is NoteOrderType.Date -> "Date"
                    is NoteOrderType.Title -> "Title"

                }
            )
            edit.apply()
        }
        get() {
            return when (share.getString("NoteOrderType", "Date")) {
                "Color" -> NoteOrderType.Color
                "Title" -> NoteOrderType.Title
                else -> NoteOrderType.Date
            }
        }
}