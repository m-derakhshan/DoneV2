package m.derakhshan.done.feature_note.domain.use_case

import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class RestoreNoteUseCase @Inject constructor(private val repository: NoteRepository) {

    suspend operator fun invoke(note: NoteModel) {
        repository.restoreNote(note = note)
    }

}