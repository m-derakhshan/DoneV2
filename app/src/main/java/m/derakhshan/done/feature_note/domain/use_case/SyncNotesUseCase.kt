package m.derakhshan.done.feature_note.domain.use_case

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import m.derakhshan.done.feature_note.domain.model.NoteSyncModel
import m.derakhshan.done.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class SyncNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(notes: List<NoteSyncModel>) {
        val execAll = ArrayList<Deferred<Unit>>()
        coroutineScope {
            for (note in notes) {
                execAll.add(async { repository.syncNotes(note = note) })
            }
        }
        execAll.awaitAll()
    }
}