package m.derakhshan.done.feature_note.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import m.derakhshan.done.feature_authentication.data.data_source.dao.UserDao
import m.derakhshan.done.feature_note.data.data_source.NoteDao
import m.derakhshan.done.feature_note.data.data_source.NoteSyncDao
import m.derakhshan.done.feature_note.domain.model.*
import m.derakhshan.done.feature_note.domain.repository.NoteRepository
import java.util.concurrent.CancellationException

class NoteRepositoryImpl(
    private val noteDao: NoteDao,
    private val noteSyncDao: NoteSyncDao,
    private val userDao: UserDao,
    private val storage: FirebaseFirestore,
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
        val uid = userDao.getUserId()
        noteSyncDao.insert(noteSyncModel = note.toNoteSyncModel(uid, NoteSyncAction.Delete))
    }

    override suspend fun restoreNote(note: NoteModel) {
        noteDao.insert(note = note)
        val uid = userDao.getUserId()
        noteSyncDao.delete(noteSyncModel = note.toNoteSyncModel(uid, NoteSyncAction.Delete))
    }

    @Throws
    override suspend fun insertNote(note: NoteModel) {
        when {
            note.title.isBlank() -> throw InvalidNoteException("Note title can't left blank!")
            note.content.isBlank() -> throw InvalidNoteException("Note content can't left blank!")
        }
        noteDao.insert(note = note)
        val uid = userDao.getUserId()
        noteSyncDao.insert(noteSyncModel = note.toNoteSyncModel(uid, NoteSyncAction.Insert))
    }

    override suspend fun insertNote(notes: List<NoteModel>) {
        noteDao.insertAll(notes = notes)
    }

    override suspend fun getNoteById(noteId: Int): NoteModel? {
        return noteDao.getNoteById(id = noteId)
    }

    override fun getNoteToSync(): Flow<List<NoteSyncModel>?> {
        return noteSyncDao.getNotesToSync()
    }

    override suspend fun syncNotes(note: NoteSyncModel) {
        try {
            when (note.action) {
                is NoteSyncAction.Insert, is NoteSyncAction.Update -> {
                    storage.collection("users")
                        .document(note.userId).collection("notes")
                        .document(note.noteId.toString()).set(note.toNoteModel()).await()
                    noteSyncDao.delete(note)
                }
                is NoteSyncAction.Delete -> {
                    storage.collection("users")
                        .document(note.userId).collection("notes")
                        .document(note.noteId.toString()).delete().await()
                    noteSyncDao.delete(note)
                }
            }
        } catch (e: Exception) {
            if (e is CancellationException)
                throw e
            Log.i("Log", "syncNotes: error in Syncing:${e.message}")
        }
    }
}