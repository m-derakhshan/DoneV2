package m.derakhshan.done.feature_note.domain.use_case

data class AddEditNoteUseCases(
    val insertNoteUseCase: InsertNoteUseCase,
    val getNoteById:GetNoteById
)