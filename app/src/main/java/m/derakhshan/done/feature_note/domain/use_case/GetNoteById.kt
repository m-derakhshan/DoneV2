package m.derakhshan.done.feature_note.domain.use_case

import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteById @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(noteId: Int): NoteModel? {
        return repository.getNoteById(noteId = noteId)
    }
}