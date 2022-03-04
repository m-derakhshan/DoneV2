package m.derakhshan.done.feature_note.data.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_note.domain.model.NoteModel

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notes: List<NoteModel>)

    @Delete
    suspend fun delete(note: NoteModel)

    @Query("SELECT * FROM NoteModel")
    fun getNotes(): Flow<List<NoteModel>?>

    @Query("SELECT * FROM NoteModel WHERE id=:id")
    suspend fun getNoteById(id: Int): NoteModel?
}