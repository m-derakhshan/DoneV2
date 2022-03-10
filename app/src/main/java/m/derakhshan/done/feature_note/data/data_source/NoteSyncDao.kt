package m.derakhshan.done.feature_note.data.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_note.domain.model.NoteSyncModel

@Dao
interface NoteSyncDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteSyncModel: NoteSyncModel)

    @Delete
    suspend fun delete(noteSyncModel: NoteSyncModel)

    @Query("DELETE FROM NoteSyncModel")
    suspend fun deleteAll()

    @Query("SELECT * FROM NoteSyncModel")
    fun getNotesToSync(): Flow<List<NoteSyncModel>?>

}