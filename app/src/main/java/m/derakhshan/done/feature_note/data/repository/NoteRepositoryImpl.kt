package m.derakhshan.done.feature_note.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import m.derakhshan.done.feature_note.data.data_source.NoteDao
import m.derakhshan.done.feature_note.domain.model.InvalidNoteException
import m.derakhshan.done.feature_note.domain.model.NoteModel
import m.derakhshan.done.feature_note.domain.model.NoteOrderSortType
import m.derakhshan.done.feature_note.domain.model.NoteOrderType
import m.derakhshan.done.feature_note.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getNotes(
        orderType: NoteOrderType,
        sortType: NoteOrderSortType,
        keyword: String
    ): Flow<List<NoteModel>?> {

        val dao =
            if (keyword.isBlank()) noteDao.getNotes()
            else noteDao.searchNotes(keyword = keyword)

        return dao.map { items ->
            when (orderType) {
                NoteOrderType.Color ->
                    if (sortType is NoteOrderSortType.Ascending)
                        items?.sortedBy { it.color }
                    else
                        items?.sortedByDescending { it.color }
                NoteOrderType.Date ->
                    if (sortType is NoteOrderSortType.Ascending)
                        items?.sortedBy { it.timestamp }
                    else
                        items?.sortedByDescending { it.timestamp }
                NoteOrderType.Title ->
                    if (sortType is NoteOrderSortType.Ascending)
                        items?.sortedBy { it.title }
                    else
                        items?.sortedByDescending { it.title }
            }
        }
    }

    override suspend fun deleteNote(note: NoteModel) {
        noteDao.delete(note = note)
    }

    override suspend fun restoreNote(note: NoteModel) {
        noteDao.insert(note = note)
    }

    @Throws
    override suspend fun insertNote(note: NoteModel) {
        when {
            note.title.isBlank() -> throw InvalidNoteException("Note title can't left blank!")
            note.content.isBlank() -> throw InvalidNoteException("Note content can't left blank!")
        }
        noteDao.insert(note = note)
    }

    override suspend fun insertNote(notes: List<NoteModel>) {
        noteDao.insertAll(notes = notes)
    }

    override suspend fun getNoteById(noteId: Int): NoteModel? {
        return noteDao.getNoteById(id = noteId)
    }
}