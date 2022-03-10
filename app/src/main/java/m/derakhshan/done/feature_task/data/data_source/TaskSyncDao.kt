package m.derakhshan.done.feature_task.data.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import m.derakhshan.done.feature_task.domain.model.TaskSyncModel

@Dao
interface TaskSyncDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskSyncModel: TaskSyncModel)

    @Delete
    suspend fun delete(taskSyncModel: TaskSyncModel)

    @Query("DELETE FROM TaskSyncModel")
    suspend fun deleteAll()

    @Query("SELECT * FROM TaskSyncModel")
    fun getTaskToSync(): Flow<List<TaskSyncModel>?>

}