package m.derakhshan.done.feature_note.data.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_note.data.data_source.NoteDao
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.model.NoteOrderSortType
import m.derakhshan.done.feature_note.domain.model.NoteOrderType
import m.derakhshan.done.feature_note.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getNotes(
        orderType: NoteOrderType,
        sortType: NoteOrderSortType
    ): Flow<List<NoteModel>?> {
        return noteDao.getNotes()
    }

    override suspend fun deleteNote(note: NoteModel) {
        noteDao.delete(note = note)
    }

    override suspend fun restoreNote(note: NoteModel) {
        noteDao.insert(note = note)
    }

    override suspend fun insertNote(note: NoteModel) {
        noteDao.insert(note = note)
    }

    override suspend fun insertNote(notes: List<NoteModel>) {
        noteDao.insertAll(notes = notes)
    }
}