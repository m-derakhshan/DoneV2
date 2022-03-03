package m.derakhshan.done.feature_note.domain.model

sealed class NoteOrderSortType {
    object Ascending : NoteOrderSortType()
    object Descending : NoteOrderSortType()
}
