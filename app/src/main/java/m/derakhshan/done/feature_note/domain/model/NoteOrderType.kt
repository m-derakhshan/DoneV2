package m.derakhshan.done.feature_note.domain.model

sealed class NoteOrderType {
    object Color : NoteOrderType()
    object Date : NoteOrderType()
    object Title : NoteOrderType()
}
