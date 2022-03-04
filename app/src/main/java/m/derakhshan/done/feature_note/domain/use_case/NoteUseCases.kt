package m.derakhshan.done.feature_note.domain.use_case


data class NoteUseCases(
    val deleteNote: DeleteNoteUseCase,
    val getNotes: GetNotesUseCase,
    val restoreNote: RestoreNoteUseCase
)