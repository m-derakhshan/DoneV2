package m.derakhshan.done.feature_note.domain.use_case

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_note.domain.model.NoteSyncModel
import m.derakhshan.done.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesToSyncUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<NoteSyncModel>?> {
        return repository.getNoteToSync()
    }
}